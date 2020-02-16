package com.netcracker.db.repository;

import com.netcracker.db.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsRepository extends JpaRepository<Point, Integer> {
    Point findPointsByPointID(Integer pointID);

    List<Point> findPointsByRouteID(Integer routeID);

}
