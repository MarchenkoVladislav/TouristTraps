package com.netcracker.controller;

import com.netcracker.db.entity.Photo;
import com.netcracker.db.entity.Point;
import com.netcracker.db.entity.Review;
import com.netcracker.db.entity.Route;
import com.netcracker.db.service.PhotosService;
import com.netcracker.db.service.RoutesService;
import com.netcracker.db.service.UsersService;
import com.netcracker.exception.InvalidFormException;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/routes")
public class RoutesController {
    @Autowired
    private RoutesService routesService;

    @GetMapping(name ="/findAllRoutes", value = "")
    public List<Route> findAllRoutes() {
        return routesService.findAllRoutes();
    }

    @GetMapping(name = "/findRouteByID", value = "/{routeID}")
    public Route findRouteByID(@PathVariable Integer routeID) {
        Route route = routesService.findRouteByID(routeID);
        if (route == null) {
            throw new NotFoundException();
        }
        else return route;
    }

    @GetMapping(name="/findByUserID", value="findByUserIDAndRouteID/{userID}/{routeID}")
    public Route findRoutesByUserID(@PathVariable String userID, @PathVariable Integer routeID) {
        return routesService.findRouteByUserIDAndRouteID(userID,routeID);
    }

    @GetMapping(name ="/findRoutesInTheArea", value = "/findRoutesInTheArea")
    public List<Route> findRoutesInTheArea(@RequestParam Double southWestX, @RequestParam Double southWestY,
                                           @RequestParam Double northEastX, @RequestParam Double northEastY) {

        return routesService.findRoutesInTheArea(southWestX, southWestY, northEastX, northEastY);
    }

    @PostMapping(name = "/createRoute",value = "/createRoute")
    public void createRoute(HttpSession session, @RequestParam String userID, @RequestParam String routeName,
                             @RequestParam String routeShortDescription, @RequestParam String routeFullDescription,
                             @RequestParam String points, @RequestParam String color) {
        List<Point> pointList = new ArrayList<>();
        String pointString = points.replace("[", "]").replace("]", "");
        String [] coordinates = pointString.split(",");
        double x = 0d;
        for (int i = 0; i < coordinates.length; i++) {
            if (i % 2 == 0) {
                x = Double.parseDouble(coordinates[i]);
            }
            else {
                pointList.add(new Point(x, Double.parseDouble(coordinates[i])));
            }
        }
        Route route = routesService.createOrUpdateRoute(new Route(routeName, routeShortDescription, routeFullDescription, pointList, color, userID));
        session.setAttribute("routeId", route.getRouteID());
    }

    @PutMapping(name ="/updateRouteByID", value = "/{routeID}/updateRoute")
    public void updateRouteByID(@PathVariable Integer routeID, @RequestParam String routeName,
                                  @RequestParam String routeShortDescription, @RequestParam String routeFullDescription) {
        if (!validForm(routeName,routeShortDescription,routeFullDescription)){
            throw new InvalidFormException("Forbidden !");
        }
        else {
            routesService.updateRouteByID(routeID, routeName, routeShortDescription, routeFullDescription);
        }
    }

    @DeleteMapping(name ="/deleteRouteByID", value = "/{routeID}")
    public void deleteRouteByID(@PathVariable Integer routeID) {
        if (routesService.findRouteByID(routeID) == null) {
            throw new NotFoundException();
        }
        else {
            routesService.deleteRouteByID(routeID);
        }
    }

    @PutMapping(name ="/addReviewToRoute", value = "/{routeID}/addReview")
    public void addReviewToRoute(@PathVariable Integer routeID, @RequestParam String reviewText, @RequestParam String userID,
                                 @RequestParam Double routeMark) {
        if (routesService.findRouteByID(routeID) == null) {
            throw new NotFoundException();
        }
        else routesService.addReviewToRoute(routeID, new Review(routeMark,userID, reviewText));
    }

    @PutMapping(name ="/addPointToRoute", value = "/{routeID}/addPoint")
    public void addPointToRoute(@PathVariable Integer routeID, @RequestBody Point point) {
        if (routesService.findRouteByID(routeID) == null) {
            throw new NotFoundException();
        }
        else routesService.addPointToRoute(routeID, point);
    }

    @PutMapping(name ="/addPhotoToRoute", value = "/{routeID}/addPhoto")
    public void addPhotoToRoute(@PathVariable Integer routeID, @RequestBody Photo photo) {
        if (routesService.findRouteByID(routeID) == null) {
            throw new NotFoundException();
        }
        else routesService.addPhotoToRoute(routeID, photo);
    }

    @GetMapping(name = "/getRouteMark", value = "/{routeID}/getMark")
    public Double getRouteMark(@PathVariable Integer routeID) {
        return routesService.getRouteMarkByRouteID(routeID);
    }

    private boolean validForm(String string1,String string2, String string3) {
        boolean valid = true;
        char [] char1 = string1.toCharArray();
        char [] char2 = string2.toCharArray();
        char [] char3 = string3.toCharArray();

        if (char1[0]==' ' || char2[0]==' ' || char3[0]==' ' ||
            char1.length <3 || char2.length <3 || char3.length <3){
            valid = false;
        }

        return valid;
    }
}
