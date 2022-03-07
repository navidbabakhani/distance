package com.wcc.distance.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

public class DistanceResponseDto {

    @NonNull
    private Location firstLocation;
    @NonNull
    private Location secondLocation;
    private double distance;
    private Unit unit = Unit.KM;

    public DistanceResponseDto(@NonNull Location firstLocation, @NonNull Location secondLocation, double distance) {
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.distance = distance;
    }

    @NonNull
    public Location getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(@NonNull Location firstLocation) {
        this.firstLocation = firstLocation;
    }

    @NonNull
    public Location getSecondLocation() {
        return secondLocation;
    }

    public void setSecondLocation(@NonNull Location secondLocation) {
        this.secondLocation = secondLocation;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}

enum Unit {
    @JsonProperty("km") KM,
    @JsonProperty("mile") MILE;
}
