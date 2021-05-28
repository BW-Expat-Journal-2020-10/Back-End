package com.lambdaschool.expat.services;

import com.lambdaschool.expat.exceptions.ResourceNotFoundException;
import com.lambdaschool.expat.models.Story;
import com.lambdaschool.expat.repository.StoryRepository;
import com.lambdaschool.expat.views.StoryCategoryList;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "productService")
public class StoryServiceImpl implements StoryService {
    /**
     * Connects this service to the products table
     * Used in place of @Autowired
     */
    private final StoryRepository storyRepository;

    public StoryServiceImpl(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }
/**
    @Override
    public List<StoryCategoryList> getStoryCategoryList() {
        List<StoryCategoryList> returnList = storyRepository.getStoryCategoryList();
        return returnList;
    }
**/
    /**
     * Finds a list of all products in the database
     *
     * @return a list of all products in the database
     */
    @Override
    public List<Story> findAllStory() {
        List<Story> storyList = new ArrayList<>();
        storyRepository.findAll().iterator().forEachRemaining(storyList::add);
        return storyList;
    }

    /**
     * Finds the specified product based on the productId provided
     *
     * @param storyId the productId associated with the object you seek
     * @return returns the product object associated with the provided productId
     */
    @Override
    public Story findByStoryId(long storyId) {
        return storyRepository
                .findById(storyId)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Story ID " + storyId + " not found!"
                                )
                );
    }

    /**
     * Removes a product from the database based on the productId provided
     *
     * @param storyId The primary key (long) of the product to be removed
     */
    @Transactional
    @Override
    public void delete(long storyId) {
        Story story = findByStoryId(storyId);
        storyRepository.delete(story);
    }

    /**
     * Deletes all records from the products table
     * Used primarily to clear the table before seeding with test data
     */
    @Transactional
    @Override
    public void deleteAll() {
        storyRepository.deleteAll();
    }
}

