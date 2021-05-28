package com.lambdaschool.expat.services;

import com.lambdaschool.expat.models.Story;
import com.lambdaschool.expat.views.StoryCategoryList;
import java.util.List;

public interface StoryService {
    List<Story> findAllStory();
/**
    List<StoryCategoryList> getStoryCategoryList();
**/
    Story findByStoryId(long storyId);

    void delete(long storyId);

    void deleteAll();
}
