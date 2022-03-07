package com.wcc.distance;

import com.wcc.distance.model.Point;

import java.io.IOException;
import java.io.InputStream;

public class TestData {

    public static final String POST_CODE_1 = "YO42 4NG";
    public static final String POST_CODE_2 = "YO8 9UA";

    public static final Point POINT_1 = new Point(53.873455, -0.813476);
    public static final Point POINT_2 = new Point(53.774, -1.119);

    public static String readText(String resource) {
        try (InputStream inputStream = TestData.class.getResource(resource).openStream()) {
            return new String(inputStream.readAllBytes());
        } catch (Exception e) {
            return "";
        }
    }
}
