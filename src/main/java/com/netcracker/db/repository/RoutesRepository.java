package com.netcracker.db.repository;

import com.netcracker.db.entity.Review;
import com.netcracker.db.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoutesRepository extends JpaRepository<Route, Integer> {
    Route findRoutesByRouteID(Integer routeID);

    Route findRouteByUserIDAndRouteID(String userId, Integer routeID);

    @Query(value = "select * from Routes where Routes.route_id in (select distinct Routes.route_id from Routes join Points on Points.route_id = Routes.route_id where Points.point_x >= :southWestX and Points.point_x <= :northEastX " +
            "and Points.point_y >= :southWestY and Points.point_y <= :northEastY) order by Routes.average_route_mark desc limit 50", nativeQuery = true)
    List<Route> findRoutesInTheArea(double southWestX, double southWestY, double northEastX, double northEastY);



}
