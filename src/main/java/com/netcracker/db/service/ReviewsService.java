package com.netcracker.db.service;

import com.netcracker.db.entity.Review;
import com.netcracker.db.entity.Route;
import com.netcracker.db.repository.ReviewsRepository;
import com.netcracker.db.repository.RoutesRepository;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsService {
    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private RoutesRepository routesRepository;

    public Review createOrUpdateReview(Review review) {
        Review review1 = reviewsRepository.save(review);
        Route route = routesRepository.findRoutesByRouteID(review.getRouteID());
        route.setAverageRouteMark(reviewsRepository.getAverageRouteMark(route.getRouteID()));
        routesRepository.save(route);
        return review1;
    }

    public Review findReviewByID(Integer reviewID) {
        return reviewsRepository.findReviewsByReviewID(reviewID);
    }

    public Page<Review> findReviewsByRouteID(Integer routeID, Pageable pageable) {
        return reviewsRepository.findReviewsByRouteID(routeID, pageable);
    }

    public List<Review> findReviewsByRouteID(Integer routeID) {
        return reviewsRepository.findReviewsByRouteID(routeID);
    }

    public List<Review> findReviewsByUserIDAndRouteID(String userID, Integer routeID) {
        return reviewsRepository.findReviewsByUserIDAndAndRouteID(userID, routeID);
    }

    public List<Review> findAllReviews() {
        return reviewsRepository.findAll();
    }

    public Review updateReviewByID(Integer reviewID, Review review) {
        Review review1 = findReviewByID(reviewID);
        if (review1 == null) {
            throw new NotFoundException();
        }
        else {
            if (Double.compare(review.getRouteMark(), 0.0) != 0) {
                review1.setRouteMark(review.getRouteMark());
            }
            if (review.getReviewText().length() != 0) {
                review1.setReviewText(review.getReviewText());
            }
            return createOrUpdateReview(review1);
        }
    }

    public void deleteReviewByID(Integer reviewID) {
        Review review = reviewsRepository.findReviewsByReviewID(reviewID);
        Integer routeId = review.getRouteID();
        reviewsRepository.deleteById(reviewID);
        Double mark = reviewsRepository.getAverageRouteMark(routeId);
        Route route = routesRepository.findRoutesByRouteID(routeId);
        if (mark == null) {
            route.setAverageRouteMark(0d);
        }
        else {
            route.setAverageRouteMark(mark);
        }
        routesRepository.save(route);
    }

    public Double getAverageRouteMark(Integer routeID) {
        return reviewsRepository.getAverageRouteMark(routeID);
    }
}
