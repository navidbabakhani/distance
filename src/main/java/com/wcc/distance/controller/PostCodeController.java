package com.wcc.distance.controller;

import com.wcc.distance.controller.dto.Location;
import com.wcc.distance.exception.PostCodeNotFoundException;
import com.wcc.distance.model.Point;
import com.wcc.distance.service.PostCodeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class PostCodeController {

    private final PostCodeService postCodeService;

    public PostCodeController(PostCodeService postCodeService) {
        this.postCodeService = postCodeService;
    }

    @PostMapping("/update")
    public Location updatePostCode(@RequestParam String postalCode,
                                   @RequestBody @Valid Point coordinates) {
        if (postCodeService.updateCoordinates(postalCode, coordinates)) {
            return new Location(postalCode, coordinates.getLatitude(), coordinates.getLongitude());
        } else {
            throw new PostCodeNotFoundException(postalCode);
        }
    }
}
