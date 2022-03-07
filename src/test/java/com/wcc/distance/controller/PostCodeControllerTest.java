package com.wcc.distance.controller;

import com.wcc.distance.service.PostCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.wcc.distance.TestData.*;
import static org.mockito.Mockito.when;

@WebFluxTest(PostCodeController.class)
class PostCodeControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private PostCodeService postCodeService;

    @Test
    void canUpdateCoordinates() {
        when(postCodeService.updateCoordinates(POST_CODE_1, POINT_1)).thenReturn(Boolean.TRUE);

        client.post()
                .uri(builder -> builder
                        .path("/update")
                        .queryParam("postalCode", POST_CODE_1)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(POINT_1))
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(readText("/contracts/update_success.json"));
    }

}