package com.wcc.distance.controller;

import com.wcc.distance.controller.dto.DistanceResponseDto;
import com.wcc.distance.controller.dto.Location;
import com.wcc.distance.model.Point;
import com.wcc.distance.service.PostCodeService;
import com.wcc.distance.util.DistanceUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistanceController {

    private final PostCodeService postCodeService;

    public DistanceController(PostCodeService postCodeService) {
        this.postCodeService = postCodeService;
    }

    @GetMapping("/distance")
    public DistanceResponseDto getDistance(@RequestParam String firstPostalCode,
                                           @RequestParam String secondPostalCode) {

        Point point1 = postCodeService.getPointFromPostalCode(firstPostalCode);
        Point point2 = postCodeService.getPointFromPostalCode(secondPostalCode);

        return new DistanceResponseDto(
                new Location(firstPostalCode, point1.getLatitude(), point1.getLongitude()),
                new Location(secondPostalCode, point2.getLatitude(), point2.getLongitude()),
                DistanceUtil.calculateDistance(point1, point2)
        );
    }
}
