package com.netcracker.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @Column(name = "review_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;

    @Column(nullable = false)
    private double routeMark;

    @Column(name = "review_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date reviewDate;

    @Column
    private String reviewText;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "route_id")
    private Integer routeID;

    public Review(double routeMark, String reviewText) {
        this.routeMark = routeMark;
        this.reviewText = reviewText;
        this.reviewDate = new Date();
    }

    public Review(double routeMark, String reviewText, Integer routeID) {
        this.routeMark = routeMark;
        this.reviewText = reviewText;
        this.routeID = routeID;
        this.reviewDate = new Date();
    }

    public Review(double routeMark,String userID, String reviewText) {
        this.routeMark = routeMark;
        this.reviewText = reviewText;
        this.userID = userID;
        this.reviewDate = new Date();
    }

    public Review(double routeMark, String reviewText, String userID, Integer routeID) {
        this.routeMark = routeMark;
        this.reviewText = reviewText;
        this.userID = userID;
        this.routeID = routeID;
        this.reviewDate = new Date();
    }
}
