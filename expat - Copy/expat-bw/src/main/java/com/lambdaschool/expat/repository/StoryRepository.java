package com.lambdaschool.expat.repository;

import com.lambdaschool.expat.models.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends CrudRepository<Story, Long> {}