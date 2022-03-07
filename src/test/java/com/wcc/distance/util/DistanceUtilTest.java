package com.wcc.distance.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.wcc.distance.TestData.POINT_1;
import static com.wcc.distance.TestData.POINT_2;

class DistanceUtilTest {

    @Test
    void calculateDistance() {
        Assertions.assertEquals(22.90034134175489, DistanceUtil.calculateDistance(POINT_1, POINT_2));
    }
}