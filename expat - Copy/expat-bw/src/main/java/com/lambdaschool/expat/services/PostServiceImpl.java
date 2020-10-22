package com.lambdaschool.expat.services;

import com.lambdaschool.expat.exceptions.ResourceNotFoundException;
import com.lambdaschool.expat.models.Post;
import com.lambdaschool.expat.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements ItemService Interface
 */
@Transactional
@Service(value = "itemService")
public class PostServiceImpl implements PostService {
    /**
     * Connects this service to the Item table
     * Used in place of @Autowire
     */
    private final PostRepository postRepository;

    /**
     * Connects this service to the HelpFunctions service
     * Used in place of @Autowire
     */
    private final HelperFunctions helperFunctions;

    public PostServiceImpl(
            PostRepository postRepository,
            HelperFunctions helperFunctions
    ) {
        this.postRepository = postRepository;
        this.helperFunctions = helperFunctions;
    }

    /**
     * Finds a list of items associated with a specific user
     *
     * @param userId the userId of the user who's items you're looking for
     * @return returns a list of all items associated with the specified user
     */
    @Override
    public List<Post> findByUserId(long userId) {
        List<Post> returnList = new ArrayList<>();
        postRepository
                .findByUserUserId(userId)
                .iterator()
                .forEachRemaining(returnList::add);
        return returnList;
    }

    /**
     * Finds a list of all items in the database
     * @return returns a list of all items in the database
     */
    @Override
    public List<Post> findAllPosts() {
        List<Post> returnList = new ArrayList<>();
        postRepository.findAll().iterator().forEachRemaining(returnList::add);
        return returnList;
    }

    /**
     * Finds the specified item based on the itemId provided
     * @param itemId the itemId associated with the object you seek
     * @return returns the item object associated with the provided itemId
     */
    @Override
    public Post findPostById(long postId) {
        return postRepository
                .findById(postId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post ID " + postId + " not found!")
                );
    }

    /**
     * Saves a new item to the database
     * @param item the item object to be saved
     * @return returns the saved item
     */
    @Transactional
    @Override
    public Post save(Post post) {
        // Create a new item object
        Post newPost = new Post();

        // Set local fields
        newPost.setName(post.getName());
        newPost.setDescription(post.getDescription());


        // Set joins
        newPost.setUser(post.getUser());
        newPost.setPhoto(post.getPhoto());
        newPost.setStory(post.getStory());

        return postRepository.save(newPost);
    }

    /**
     * Updates and existing item in the database with new information
     * @param item just the item fields to be updated.
     * @param itemId   The primary key (long) of the item to update
     * @return returns the updated item
     */
    @Transactional
    @Override
    public Post update(Post post, long postId) {
        // Get the current item object from the database
        Post currentItem = findPostById(postId);

        // Check if the current user is authorized to make the change
        if (
                helperFunctions.isAuthorizedToMakeChange(post.getUser().getUsername())
        ) {
            // Check if the incoming object has a name and update if yes
            if (post.getName() != null) {
                currentItem.setName(post.getName());
            }

            // Check if the incoming object has a description and update if yes
            if (post.getDescription() != null) {
                currentItem.setDescription(post.getDescription());
            }


            // Check if the incoming object has a market and update if yes
            if (post.getPhoto() != null) {
                currentItem.setPhoto(post.getPhoto());
            }

            // Check if the incoming object has a product and update if yes
            if (post.getStory() != null) {
                currentItem.setStory(post.getStory());
            }

            // Save the updated item to database
            return postRepository.save(currentItem);
        } else {
            // note we should never get to this line but is needed for the compiler
            // to recognize that this exception can be thrown
            throw new ResourceNotFoundException(
                    "This user is not authorized to make change"
            );
        }
    }

    /**
     * Removes an item from the database based on the itemId provided
     * @param itemId The primary key (long) of the item to be removed
     */
    @Transactional
    @Override
    public void deletePostById(long postId) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("post number " + postId + " not found!")
                );
        // Check if the current user is authorized to make the change
        if (
                helperFunctions.isAuthorizedToMakeChange(post.getUser().getUsername())
        ) {
            // Remove the item
            postRepository.deleteById(postId);
        } else {
            // note we should never get to this line but is needed for the compiler
            // to recognize that this exception can be thrown
            throw new ResourceNotFoundException(
                    "This user is not authorized to make change"
            );
        }
    }

    /**
     * Deletes all records from the items table
     * Used primarily to clear the table before seeding with test data
     */
    @Transactional
    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }
}