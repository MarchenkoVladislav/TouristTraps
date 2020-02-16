package com.netcracker.controller;

import com.netcracker.db.entity.Review;
import com.netcracker.db.service.ReviewsService;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @GetMapping(name ="/findAllReviews", value = "")
    public List<Review> findAllReviews() {
        return reviewsService.findAllReviews();
    }

    @GetMapping(name = "/findReviewByID", value = "reviewID/{reviewID}")
    public Review findReviewByID(@PathVariable Integer reviewID) {
        Review review = reviewsService.findReviewByID(reviewID);
        if (review == null) {
            throw new NotFoundException();
        }
        else return review;
    }


    @GetMapping(name = "/findReviewByRouteID", value = "/findByRouteID/{routeID}")
    public List<Review> findReviewByRouteID(@PathVariable Integer routeID) {
        List<Review> review = reviewsService.findReviewsByRouteID(routeID);
        if (review == null) {
            throw new NotFoundException();
        }
        else return review;
    }

    @GetMapping(name="/findReviewByRouteIDAndUserID", value="/{routeID}/{userID}")
    public List<Review> findReviewByRouteIDAndUserID(@PathVariable Integer routeID, @PathVariable String userID) {
        return reviewsService.findReviewsByUserIDAndRouteID(userID, routeID);
    }

    @PostMapping(name = "/createReview",value = "/addReview")
    public Review createReview(@RequestParam String userID, @RequestParam Integer routeID,
                               @RequestParam Double routeMark, @RequestParam String reviewText) {
        return reviewsService.createOrUpdateReview(new Review(routeMark, reviewText, userID, routeID));
    }

    @PutMapping(name ="/updateReviewByID", value = "/updateReview/{reviewID}")
    public Review updateReviewByID(@PathVariable Integer reviewID, @RequestParam Double routeMark, @RequestParam String reviewText) {
      return reviewsService.updateReviewByID(reviewID, new Review(routeMark, reviewText));
    }

    @DeleteMapping(name ="/deleteReviewByID", value = "/deleteReview/{reviewID}")
    public void deleteReviewByID(@PathVariable Integer reviewID) {
        reviewsService.deleteReviewByID(reviewID);
    }

    @GetMapping(name ="getAverageRouteMark", value = "/{routeID}/getMark")
    public Double getAverageRouteMark(@PathVariable Integer routeID) {
        return reviewsService.getAverageRouteMark(routeID);
    }
}

