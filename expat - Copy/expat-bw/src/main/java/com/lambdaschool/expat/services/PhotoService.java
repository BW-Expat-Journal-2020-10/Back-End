package com.lambdaschool.expat.services;

import com.lambdaschool.expat.models.Photo;
import java.util.List;

public interface PhotoService {
    List<Photo> findAllPhotos();

    Photo findByPhotoId(long photoId);

    void delete(long photoId);

    void deleteAll();
}