package com.netcracker.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Photos")
public class Photo {
    @Id
    @Column(name = "photo_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoID;

    @Column(nullable = false)
    private String photoName;

    @Column(nullable = false)
    private String pathToPhoto;

    @Column(name = "route_id")
    private Integer routeID;

    public Photo(Integer routeID, String photoName, String pathToPhoto) {
        this.routeID = routeID;
        this.photoName = photoName;
        this.pathToPhoto = pathToPhoto;
    }

    public Photo(String photoName, String pathToPhoto) {
        this.photoName = photoName;
        this.pathToPhoto = pathToPhoto;
    }
}
