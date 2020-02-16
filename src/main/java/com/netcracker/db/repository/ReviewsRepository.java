package com.netcracker.db.repository;

import com.netcracker.db.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Review, Integer> {
    Review findReviewsByReviewID(Integer reviewID);

    Page<Review> findReviewsByRouteID(Integer routeID, Pageable pageable);

    List<Review> findReviewsByRouteID(Integer routeID);

    List<Review> findReviewsByUserIDAndAndRouteID(String userID, Integer routeID);

    @Query(value = "select avg(Reviews.route_mark) from Reviews where Reviews.route_id = :routeID", nativeQuery = true)
    Double getAverageRouteMark(Integer routeID);
}
