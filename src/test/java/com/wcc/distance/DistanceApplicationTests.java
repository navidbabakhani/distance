package com.wcc.distance;

import com.wcc.distance.controller.DistanceController;
import com.wcc.distance.controller.PostCodeController;
import com.wcc.distance.service.PostCodeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DistanceApplicationTests {

    @Autowired
    private DistanceController distanceController;
    @Autowired
    private PostCodeController postCodeController;
    @Autowired
    private PostCodeService postCodeService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(distanceController);
        Assertions.assertNotNull(postCodeController);
        Assertions.assertNotNull(postCodeService);
    }
}
