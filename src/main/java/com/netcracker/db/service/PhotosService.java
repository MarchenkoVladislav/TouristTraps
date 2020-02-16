package com.netcracker.db.service;

import com.netcracker.db.entity.Photo;
import com.netcracker.db.repository.PhotosRepository;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotosService {
    @Autowired
    private PhotosRepository photosRepository;

    public Photo createOrUpdatePhoto(Photo photo) {
        return photosRepository.save(photo);
    }

    public Photo findPhotoByID(Integer photoID) {
        return photosRepository.findPhotosByPhotoID(photoID);
    }

    public List<Photo> findPhotosByRouteID(Integer routeID) {
        return photosRepository.findPhotosByRouteID(routeID);
    }

    public List<Photo> findAllPhotos() {
        return photosRepository.findAll();
    }

    public Photo updatePhotoByID(Integer photoID, Photo photo) {
        Photo  photo1 = findPhotoByID(photoID);
        if (photo1 == null) {
            throw new NotFoundException();
        }
        else {
            photo1.setPathToPhoto(photo.getPathToPhoto());
            photo1.setPhotoName(photo.getPhotoName());
            return createOrUpdatePhoto(photo1);
        }
    }

    public void deletePhotoByID(Integer photoID) {
        photosRepository.deleteById(photoID);
    }

}
