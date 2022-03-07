package com.wcc.distance.repository;

import com.wcc.distance.model.Point;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCodesRepository {

    Point findCoordinates(String postCode);

    boolean updateCoordinates(String postCode, Point coordinates);
}
