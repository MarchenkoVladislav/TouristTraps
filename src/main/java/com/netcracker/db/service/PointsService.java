package com.netcracker.db.service;

import com.netcracker.db.entity.Point;
import com.netcracker.db.repository.PointsRepository;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsService {
    @Autowired
    private PointsRepository pointsRepository;

    public Point createOrUpdatePoint(Point point) {
        return pointsRepository.save(point);
    }

    public Point findPointByID(Integer pointID) {
        return pointsRepository.findPointsByPointID(pointID);
    }

    public List<Point> findPointByRouteID(Integer routeID) {
        return pointsRepository.findPointsByRouteID(routeID);
    }

    public List<Point> findAllPoints() {
        return pointsRepository.findAll();
    }

    public Point updatePointByID(Integer pointID, Point point) {
        Point point1 = findPointByID(pointID);

        if (point1 == null) {
            throw new NotFoundException();
        }
        else {
            point1.setPointX(point.getPointX());
            point1.setPointY(point.getPointY());
            return createOrUpdatePoint(point1);
        }
    }

    public void deletePointByID(Integer pointID) {
        pointsRepository.deleteById(pointID);
    }
}
