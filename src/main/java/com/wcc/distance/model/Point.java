package com.wcc.distance.model;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Point {
    @NotNull private Double latitude;
    @NotNull private Double longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(latitude, point.latitude) &&
                Objects.equals(longitude, point.longitude);
    }
}
