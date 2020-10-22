package com.lambdaschool.expat.services;

import com.lambdaschool.expat.models.Post;
import java.util.List;

/**
 * The Service that works with Item Model.
 * <p>
 */
public interface PostService {
    /**
     * Find a list of items associated with a specific user
     *
     * @param userId the userId of the user assigned to each item
     * @return a list of items assigned to the user
     */
    List<Post> findByUserId(long userId);

    /**
     * Find a list of all items
     *
     * @return a list of all items
     */
    List<Post> findAllPosts();

    /**
     * Find a specific item object by the provided itemId
     * @param itemId the itemId associated with the object you seek
     * @return returns the item object associated with the itemId provided
     */
    Post findPostById(long postId);

    /**
     * Given a complete item object, saves that item object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param item the item object to be saved
     * @return the saved item object including any automatically generated fields
     */
    Post save(Post post);

    /**
     * Updates the provided fields in the user record referenced by the primary key.
     * <p>
     * Regarding Market, Product, Subcategory and Category, this process only allows adding those.
     * Deleting and editing market is done through a separate endpoint.
     *
     * @param item just the item fields to be updated.
     * @param itemId   The primary key (long) of the item to update
     * @return the complete item object that got updated
     */
    Post update(Post post, long postId);

    /**
     * Removes an item from the database based on the provided itemId
     *
     * @param itemId The primary key (long) of the item to be removed
     */
    void deletePostById(long postId);

    /**
     * Removes all items from the database
     * Primarily used to wipe the database before seeding with initial data
     */
    void deleteAll();
}

