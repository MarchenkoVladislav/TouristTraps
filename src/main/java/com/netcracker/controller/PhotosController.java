package com.netcracker.controller;

import com.netcracker.db.entity.Photo;
import com.netcracker.db.service.PhotosService;
import com.netcracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/photos")
public class PhotosController {
    @Autowired
    private PhotosService photosService;

    @GetMapping(name ="/findAllPhotos", value = "")
    public List<Photo> findAllPhotos() {
        return photosService.findAllPhotos();
    }

    @GetMapping(name = "/findPhotoByID", value = "/{photoID}")
    public Photo findPhotoByID(@PathVariable Integer photoID) {
        Photo photo = photosService.findPhotoByID(photoID);
        if (photo == null) {
            throw new NotFoundException();
        }
        else return photo;
    }

    @GetMapping(name = "/findPhotoByRouteID", value = "/{routeID}")
    public List<Photo> findPhotoByRouteID(@PathVariable Integer routeID) {
        List<Photo> photos = photosService.findPhotosByRouteID(routeID);
        if (photos == null) {
            throw new NotFoundException();
        }
        else return photos;
    }


    @PostMapping(name = "/createPhoto",value = "")
    public Photo createPhoto(@RequestParam Integer routeID, @RequestParam String photoName,
                             @RequestParam String pathToPhoto) {
        return photosService.createOrUpdatePhoto(new Photo(routeID,photoName, pathToPhoto));
    }

    @PutMapping(name ="/updatePhotoByID", value = "/{photoID}/updatePhoto")
    public Photo updatePhotoByID(@PathVariable Integer photoID, @RequestParam String photoName,
                                  @RequestParam String pathToPhoto ) {
        Photo photo = photosService.updatePhotoByID(photoID, new Photo(photoName,  pathToPhoto));
        return photo;
    }

    @DeleteMapping(name ="/deletePhotoByID", value = "/{photoID}")
    public void deletePhotoByID(@PathVariable Integer photoID) {
        if (photosService.findPhotoByID(photoID) == null) {
            throw new NotFoundException();
        }
        else photosService.deletePhotoByID(photoID);
    }
}

