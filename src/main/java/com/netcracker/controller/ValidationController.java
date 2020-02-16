package com.netcracker.controller;

import com.netcracker.db.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/valid")
public class ValidationController {

    @Autowired
    private ReviewsService reviewsService;


    @GetMapping(name="/validReview", value="/validReview")
    public Map<String,Boolean> validReview(@RequestParam String userID, @RequestParam Integer routeID, @RequestParam String reviewText, @RequestParam Double routeMark) {
        Map<String, Boolean> checkStrings = new HashMap<>();
        checkStrings.put("userID", validUserID(userID, routeID));
        checkStrings.put("reviewText", validReviewText(reviewText));
        checkStrings.put("routeMark", validRouteMark(routeMark));
        return checkStrings;
    }

    private boolean validUserID(String userID, Integer routeID) {
        return (reviewsService.findReviewsByUserIDAndRouteID(userID, routeID).size()== 0);
    }

    private boolean validReviewText(String reviewText) {
        return reviewText.length() <= 255;
    }

    private boolean validRouteMark(Double routeMark) {
        return routeMark != null && routeMark > 0d && routeMark <= 5d;
    }



}
