# Getting Started

This is a simple Java RESTful microservice to calculate geographic distance between two PostCodes in the UK.

### Used Technology
* Spring Boot version 2.6.4
* Apache Lucene 9.0.0 for storing, indexing and searching within postal codes
* Spring Boot Actuator for health and http trace functionality
* Spring WebFlux (for REST controller tests)
* Swagger API documentation
* Postman collection for endpoints

### Setup and Run
After cloning this project and opening in an IDE like eclipse or IntelliJ IDEA, you can simply setup JDK, preferrably 11, and run DistanceApplication class.

For checking application running fine, you can open this url: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) and see: `{"status":"UP"}`
### How it works
For the first run, application will read `ukpostcodes.csv` file in the resource directory and index it in Apache Lucene. This will take approximately 15 to 20 seconds. The indexing (and storage) of all postcodes will be written in a directory denoting in `application.properties` file in resource folder under the key: `index.directory`.

When indexing is complete, this directory will be considered as the main storage of this microservice and any future coordinate updates will be stored here.

### Endpoints
* GET http://localhost:8080/distance
* POST http://localhost:8080/update
* GET http://localhost:8080/actuator/health
* GET http://localhost:8080/actuator/httptrace

You can find the Swagger Api documentation in `projectRoot/api/openapi.yaml` 
#### Postman Collection:
https://www.getpostman.com/collections/ec1e52635eb4b2116fdd (import them easily to Postman)