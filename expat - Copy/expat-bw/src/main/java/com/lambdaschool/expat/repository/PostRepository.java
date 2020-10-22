package com.lambdaschool.expat.repository;

import com.lambdaschool.expat.models.Post;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The CRUD repository connecting Item to the rest of the application
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    /**
     * Find a list of items associated with a specific user
     *
     * @param userId the userId of the user assigned to each item
     * @return a list of items assigned to the user
     */
    List<Post> findByUserUserId(long userId);
}