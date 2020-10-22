package com.lambdaschool.expat.controllers;

import com.lambdaschool.expat.models.Post;
import com.lambdaschool.expat.models.User;
import com.lambdaschool.expat.services.HelperFunctions;
import com.lambdaschool.expat.services.PostService;
import com.lambdaschool.expat.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class PostController {
    /**
     * Using the Item Service to process item data
     * Used in place of @Autowired
     */
    private final PostService postService;

    /**
     * Using the UserService to get user data
     * Used in place of @Autowired
     */
    private final UserService userService;

    /**
     * Connects this service to the HelpFunctions service
     * Used in place of @Autowire
     */
    private final HelperFunctions helperFunctions;

    public PostController(
            PostService postService,
            UserService userService,
            HelperFunctions helperFunctions
    ) {
        this.postService = postService;
        this.userService = userService;
        this.helperFunctions = helperFunctions;
    }

    /**
     * Returns a list of all items
     * <br>Example: http://localhost:2019/users
     * @return JSON list of all items with a status of OK
     */
    @GetMapping(value = "/posts", produces = { "application/json" })
    public ResponseEntity<?> findAllPosts() {
        List<Post> postList = postService.findAllPosts();
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    /**
     * Returns a list of items belonging to the currently authenticated user
     * <br>Example: http://localhost:2019/user/items
     * @param authentication Grabs the authentication info for the current auth token
     * @return JSON list of items belonging to the current user
     */
    @GetMapping(value = "/user/posts", produces = { "application/json" })
    public ResponseEntity<?> findByUserId(Authentication authentication) {
        User currentUser = helperFunctions.getCurrentUser();
        List<Post> postList = postService.findByUserId(currentUser.getUserId());
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    /**
     * Return a specific item based on a given itemId
     * <br>Example: http://localhost:2019/item/20
     * @param postId The itemId of the item you seek
     * @return The specific item object you seek
     */
    @GetMapping(value = "/post/{postId}", produces = { "application/json" })
    public ResponseEntity<?> getItemById(@PathVariable long postId) {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    /**
     * Given a complete item object, create a new Item object
     * <br>Example: http://localhost:2019/item
     * @param newPost A complete item object including user, Photo, and story.
     *                User, market, category, subcategory and product must already exist.
     * @return A location head with the URI to the newly created item and a status of CREATED
     */
    @PostMapping(value = "/post", consumes = { "application/json" })
    public ResponseEntity<?> addNewPost(@Valid @RequestBody Post newPost) {
        // always post as current user
        newPost.setUser(helperFunctions.getCurrentUser());

        newPost = postService.save(newPost);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPostURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{postId}")
                .buildAndExpand(newPost.getPostId())
                .toUri();
        responseHeaders.setLocation(newPostURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    /**
     * Updates the item record associated with the itemId in the request URI.
     * Only the fields in the incoming JSON object are affected.
     * <br>Example: http://localhost:2019/item/20
     * @param updatePost An object containing values for just the item fields being updated.
     * @param postId The primary key of the item you want to update.
     * @return A status of OK.
     */
    @PatchMapping(value = "/post/{postId}", consumes = { "application/json" })
    public ResponseEntity<?> updatePost(
            @RequestBody Post updatePost,
            @PathVariable long postId
    ) {
        updatePost.setUser(helperFunctions.getCurrentUser());
        postService.update(updatePost, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given item based on the itemId in the request URI.
     * <br>Example: http://localhost:2019/item/20
     * @param postId The primary key of the item you wish to delete
     * @return A status of OK
     */
    @DeleteMapping(value = "/item/{itemId}")
    public ResponseEntity<?> deletePostById(@PathVariable long postId) {
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}