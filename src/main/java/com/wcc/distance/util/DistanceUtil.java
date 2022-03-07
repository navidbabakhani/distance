package com.wcc.distance.util;

import com.wcc.distance.model.Point;

import javax.validation.constraints.NotNull;

public class DistanceUtil {

    private final static double EARTH_RADIUS = 6371;

    public static double calculateDistance(@NotNull Point point1, @NotNull Point point2) {
        double lat1Radians = Math.toRadians(point1.getLatitude());
        double lon1Radians = Math.toRadians(point1.getLongitude());
        double lat2Radians = Math.toRadians(point2.getLatitude());
        double lon2Radians = Math.toRadians(point2.getLongitude());
        double a = haversine(lat1Radians, lat2Radians) + Math.cos(lat1Radians)
                * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    private static double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private static double square(double x) {
        return x * x;
    }
}
