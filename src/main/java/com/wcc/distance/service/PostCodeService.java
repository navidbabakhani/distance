package com.wcc.distance.service;

import com.wcc.distance.model.Point;
import com.wcc.distance.repository.PostCodesRepository;
import org.springframework.stereotype.Service;

@Service
public class PostCodeService {

    private final PostCodesRepository postCodesRepository;

    public PostCodeService(PostCodesRepository postCodesRepository) {
        this.postCodesRepository = postCodesRepository;
    }

    public Point getPointFromPostalCode(String postCode) {
        return postCodesRepository.findCoordinates(postCode);
    }

    public boolean updateCoordinates(String postCode, Point coordinates) {
        return postCodesRepository.updateCoordinates(postCode, coordinates);
    }
}
