package com.netcracker.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "Routes")
public class Route {
    @Id
    @Column(name = "route_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeID;

    @Column(columnDefinition = "character varying (100)", nullable = false)
    private String routeName;

    @Column(columnDefinition = "text", nullable = false)
    private String routeShortDescription;

    @Column(columnDefinition = "text", nullable = false)
    private String routeFullDescription;

    @Column(nullable = false)
    private Double averageRouteMark;

    @Column(name = "user_id")
    private String  userID;

    @Column(nullable = false)
    private String color;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private List<Point> points = new ArrayList<>();

    public Route(String routeName, String routeShortDescription, String routeFullDescription, String color) {
        this.averageRouteMark = 0d;
        this.routeName = routeName;
        this.routeShortDescription = routeShortDescription;
        this.routeFullDescription = routeFullDescription;
        this.color = color;
    }

    public Route(String routeName, String routeShortDescription, String routeFullDescription, List<Point> points,
                 String color, String userID) {
        this.averageRouteMark = 0d;
        this.routeName = routeName;
        this.routeShortDescription = routeShortDescription;
        this.routeFullDescription = routeFullDescription;
        this.points = points;
        this.color = color;
        this.userID = userID;
    }
}
