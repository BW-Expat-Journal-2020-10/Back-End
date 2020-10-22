package com.lambdaschool.expat.services;

import com.lambdaschool.expat.exceptions.ResourceNotFoundException;
import com.lambdaschool.expat.models.Photo;
import com.lambdaschool.expat.repository.PhotoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "marketService")
public class PhotoServiceImpl implements PhotoService {
    /**
     * Connects this service to the markets table
     * Used in place of @Autowired
     */
    private final PhotoRepository photoRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    /**
     * Finds a list of all markets in the database
     * @return a list of all markets in the database
     */
    @Override
    public List<Photo> findAllPhotos() {
        List<Photo> photoList = new ArrayList<>();
        photoRepository.findAll().iterator().forEachRemaining(photoList::add);
        return photoList;
    }

    /**
     * Finds the specified market based on the marketId provided
     * @param photoId the marketId associated with the object you seek
     * @return returns the market object associated with the provided marketId
     */
    @Override
    public Photo findByPhotoId(long photoId) {
        return photoRepository
                .findById(photoId)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Photo ID " + photoId + " not found!")
                );
    }

    /**
     * Removes a market from the database based on the marketId provided
     * @param photoId The primary key (long) of the market to be removed
     */
    @Transactional
    @Override
    public void delete(long photoId) {
        Photo photo = findByPhotoId(photoId);
        photoRepository.delete(photo);
    }

    /**
     * Deletes all records from the markets table
     * Used primarily to clear the table before seeding with test data
     */
    @Transactional
    @Override
    public void deleteAll() {
        photoRepository.deleteAll();
    }
}
