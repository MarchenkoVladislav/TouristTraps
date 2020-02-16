package com.netcracker.controller;

import com.netcracker.db.entity.Point;
import com.netcracker.db.service.PointsService;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/points")
public class PointsController {
    @Autowired
    private PointsService pointsService;

    @GetMapping(name ="/findAllPoints", value = "")
    public List<Point> findAllPoints() {
        return pointsService.findAllPoints();
    }

    @GetMapping(name = "/findPointByID", value = "/{pointID}")
    public Point findPointByID(@PathVariable Integer pointID) {
        Point point = pointsService.findPointByID(pointID);
        if (point == null) {
            throw new NotFoundException();
        }
        else return point;
    }

    @GetMapping(name = "/findPointByRouteID", value = "/{routeID}")
    public List<Point> findPointByRouteID(@PathVariable Integer routeID) {
        List<Point> points = pointsService.findPointByRouteID(routeID);
        if (points == null) {
            throw new NotFoundException();
        }
        else return points;
    }

    @PostMapping(name = "/createPoint",value = " ")
    public Point createPoint(@RequestParam Double pointX, @RequestParam Double pointY) {
        return pointsService.createOrUpdatePoint(new Point(pointX, pointY));
    }

    @PutMapping(name ="/updatePointByID", value = "/{pointID}/updatePoint")
    public Point updatePointByID(@PathVariable Integer pointID, @RequestParam Double pointX, @RequestParam Double pointY ) {
        Point point = pointsService.updatePointByID(pointID, new Point(pointX, pointY));
        return point;
    }

    @DeleteMapping(name ="/deletePointByID", value = "/{pointID}")
    public void deletePointByID(@PathVariable Integer pointID) {
        if (pointsService.findPointByID(pointID) == null) {
            throw new NotFoundException();
        }
        else pointsService.deletePointByID(pointID);
    }

}

