package com.wcc.distance.controller;

import com.wcc.distance.exception.PostCodeNotFoundException;
import com.wcc.distance.service.PostCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.wcc.distance.TestData.*;
import static org.mockito.Mockito.when;

@WebFluxTest(DistanceController.class)
class DistanceControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private PostCodeService postCodeService;

    @Test
    void canGetDistance() {
        when(postCodeService.getPointFromPostalCode(POST_CODE_1)).thenReturn(POINT_1);
        when(postCodeService.getPointFromPostalCode(POST_CODE_2)).thenReturn(POINT_2);

        client.get()
                .uri(builder -> builder
                        .path("/distance")
                        .queryParam("firstPostalCode", POST_CODE_1)
                        .queryParam("secondPostalCode", POST_CODE_2)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(readText("/contracts/distance_success.json"));
    }

    @Test
    void returnBadRequestWhenParamsNotProvidedInGetDistance() {
        client.get()
                .uri(builder -> builder
                        .path("/distance")
                        .queryParam("firstPostalCode", POST_CODE_1)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().json(readText("/contracts/distance_bad_request.json"));
    }

    @Test
    void returnNotFoundWhenAPostCodeDoesNotExistInGetDistance() {
        when(postCodeService.getPointFromPostalCode(POST_CODE_1)).thenThrow(new PostCodeNotFoundException(POST_CODE_1));

        client.get()
                .uri(builder -> builder
                        .path("/distance")
                        .queryParam("firstPostalCode", POST_CODE_1)
                        .queryParam("secondPostalCode", POST_CODE_2)
                        .build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().json(readText("/contracts/distance_not_found.json"));
    }
}