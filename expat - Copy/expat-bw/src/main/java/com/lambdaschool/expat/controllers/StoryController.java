package com.lambdaschool.expat.controllers;

import com.lambdaschool.expat.services.StoryService;
import com.lambdaschool.expat.views.StoryCategoryList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoryController {
    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping(value = "/story", produces = { "application/json" })
    public ResponseEntity<?> getStoryCategoryList() {
        List<StoryCategoryList> returnList = storyService.getStoryCategoryList();
        return new ResponseEntity<>(returnList, HttpStatus.OK);
    }
}
