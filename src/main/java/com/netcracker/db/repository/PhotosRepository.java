package com.netcracker.db.repository;

import com.netcracker.db.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotosRepository extends JpaRepository<Photo, Integer > {
    Photo findPhotosByPhotoID(Integer photoID);

    List<Photo> findPhotosByRouteID(Integer routeID);
}
