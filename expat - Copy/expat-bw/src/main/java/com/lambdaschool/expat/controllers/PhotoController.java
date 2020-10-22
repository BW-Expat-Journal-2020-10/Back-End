package com.lambdaschool.expat.controllers;

import com.lambdaschool.expat.models.Photo;
import com.lambdaschool.expat.services.PhotoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(value = "/photo", produces = { "application/json" })
    public ResponseEntity<?> listAllPhoto() {
        List<Photo> photoList = photoService.findAllPhotos();
        return new ResponseEntity<>(photoList, HttpStatus.OK);
    }
}
