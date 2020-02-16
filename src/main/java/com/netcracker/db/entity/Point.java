package com.netcracker.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Points")
public class Point {
    @Id
    @Column(name = "point_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointID;

    @Column(name = "point_x", nullable = false)
    private double pointX;

    @Column(name = "point_y", nullable = false)
    private double pointY;

    @Column(name = "route_id")
    private Integer routeID;

    public Point(double pointX, double pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }
}
