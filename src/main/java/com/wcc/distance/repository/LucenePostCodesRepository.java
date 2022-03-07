package com.wcc.distance.repository;

import com.wcc.distance.exception.DuplicatePostCodeException;
import com.wcc.distance.exception.PostCodeNotFoundException;
import com.wcc.distance.model.Point;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Path;

@Repository
public class LucenePostCodesRepository implements PostCodesRepository {

    private static final Logger logger = LoggerFactory.getLogger(LucenePostCodesRepository.class);

    private static final String POST_CODE_FIELD = "PostCode";
    private static final String LATITUDE_FIELD = "Latitude";
    private static final String LONGITUDE_FIELD = "Longitude";

    private final IndexWriter writer;
    private final SearcherManager searcherManager;
    private final String indexPath;

    public LucenePostCodesRepository(@Value("${index.directory}") String indexPath) throws IOException {
        this.indexPath = indexPath;
        Directory indexDirectory = FSDirectory.open(Path.of(indexPath));
        writer = new IndexWriter(indexDirectory, new IndexWriterConfig().setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND));
        searcherManager = new SearcherManager(writer, null);
    }

    @PostConstruct
    private void indexAll() throws IOException {
        File indexFile = new File(indexPath);
        if (indexFile.listFiles() != null && indexFile.listFiles().length < 5) {
            logger.info("Indexing postal codes...");
            InputStream inputStream = LucenePostCodesRepository.class.getResourceAsStream("/ukpostcodes.csv");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(",");
                    try {
                        writer.addDocument(locationToDoc(
                                split[1],
                                Double.parseDouble(split[2]),
                                Double.parseDouble(split[3]))
                        );
                    } catch (Exception ignored) {
                    }
                }
            }
            writer.commit();
            searcherManager.maybeRefreshBlocking();
            logger.info("Indexing is finished");
        } else {
            logger.info("Index files are found.");
        }
    }

    @Override
    public Point findCoordinates(String postCode) {
        Document doc = fetchDocument(postCode);
        return new Point(
                (Double) doc.getField(LATITUDE_FIELD).numericValue(),
                (Double) doc.getField(LONGITUDE_FIELD).numericValue()
        );
    }

    @Override
    public boolean updateCoordinates(String postCode, Point coordinates) {
        fetchDocument(postCode); // to make sure postCode exists!

        Document doc = locationToDoc(postCode, coordinates.getLatitude(), coordinates.getLongitude());
        try {
            writer.updateDocument(new Term(POST_CODE_FIELD, postCode), doc);
            writer.commit();
            searcherManager.maybeRefreshBlocking();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private Document fetchDocument(String postCode) {
        Query query = new TermQuery(new Term(POST_CODE_FIELD, postCode));
        try {
            IndexSearcher indexSearcher = searcherManager.acquire();
            ScoreDoc[] scoreDocs = indexSearcher.search(query, 2).scoreDocs;
            if (scoreDocs.length == 0) {
                throw new PostCodeNotFoundException(postCode);
            }
            if (scoreDocs.length > 1) {
                throw new DuplicatePostCodeException(postCode);
            }
            return indexSearcher.doc(scoreDocs[0].doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document locationToDoc(String postCode, double latitude, double longitude) {
        Document document = new Document();
        document.add(new StringField(POST_CODE_FIELD, postCode, Field.Store.NO));
        document.add(new StoredField(LATITUDE_FIELD, latitude));
        document.add(new StoredField(LONGITUDE_FIELD, longitude));
        return document;
    }
}
