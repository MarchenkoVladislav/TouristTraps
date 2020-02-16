package com.netcracker.db.service;

import com.netcracker.db.entity.Photo;
import com.netcracker.db.entity.Point;
import com.netcracker.db.entity.Review;
import com.netcracker.db.entity.Route;
import com.netcracker.db.repository.ReviewsRepository;
import com.netcracker.db.repository.RoutesRepository;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutesService {
    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;

    public Route createOrUpdateRoute(Route route) {
        return routesRepository.save(route);
    }

    public Route findRouteByID(Integer routeID) {
        return routesRepository.findRoutesByRouteID(routeID);
    }

    public List<Route> findAllRoutes() {
        return routesRepository.findAll();
    }

    public List<Route> findRoutesInTheArea(double southWestX, double southWestY, double northEastX, double northEastY) {
        List<Route> routes = routesRepository.findRoutesInTheArea(southWestX, southWestY, northEastX, northEastY);
        return routes;
    }

    public Route findRouteByUserIDAndRouteID(String userID, Integer routeID) {
        return routesRepository.findRouteByUserIDAndRouteID(userID, routeID);
    }

    public Route updateRouteByID(Integer routeID, String routeName, String shortDescription, String fullDescription) {
        Route route1 = findRouteByID(routeID);
        if (route1 == null) {
            throw new NotFoundException();
        }
        else {
            if (!routeName.equals("")) {
                route1.setRouteName(routeName);
            }
            if (!fullDescription.equals("")) {
                route1.setRouteFullDescription(fullDescription);
            }
            if (!shortDescription.equals("")) {
                route1.setRouteShortDescription(shortDescription);
            }
            return createOrUpdateRoute(route1);
        }
    }

    public void deleteRouteByID(Integer routeID) {
        routesRepository.deleteById(routeID);
    }

    public void addPointToRoute(Integer routeID, Point point) {
        Route route = findRouteByID(routeID);

        route.getPoints().add(point);

        createOrUpdateRoute(route);
    }

    public void addReviewToRoute(Integer routeID, Review review) {
        Route route = findRouteByID(routeID);

        route.getReviews().add(review);

        createOrUpdateRoute(route);

        route.setAverageRouteMark(reviewsRepository.getAverageRouteMark(routeID));

        createOrUpdateRoute(route);
    }

    public void addPhotoToRoute(Integer routeID, Photo photo) {
        Route route = findRouteByID(routeID);

        route.getPhotos().add(photo);

        createOrUpdateRoute(route);
    }

    public Double getRouteMarkByRouteID(Integer routeID) {
        return routesRepository.findRoutesByRouteID(routeID).getAverageRouteMark();
    }
}
