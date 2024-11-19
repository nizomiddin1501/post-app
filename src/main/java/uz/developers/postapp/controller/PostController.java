package uz.developers.postapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.developers.postapp.exceptions.PostException;
import uz.developers.postapp.payload.CustomApiResponse;
import uz.developers.postapp.payload.PostDto;
import uz.developers.postapp.service.PostService;

/**
 * REST controller for managing posts, offering endpoints for
 * creating, updating, retrieving, and deleting post records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * Retrieves all posts for a specific category.
     * If no posts are found, a ResourceNotFoundException is thrown.
     *
     * @param categoryId the ID of the category
     * @return a ResponseEntity with a CustomApiResponse containing the list of PostDto
     */
    @Operation(summary = "Get Posts by Category with Pagination", description = "Retrieve a paginated list of posts by category ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts by category.")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CustomApiResponse<Page<PostDto>>> getPostsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PostDto> postDtos = postService.getPostsByCategory(categoryId, page, size);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of posts by category.",
                true,
                postDtos), HttpStatus.OK);
    }

    /**
     * Retrieves all posts for a specific user.
     * If no posts are found, a ResourceNotFoundException is thrown.
     *
     * @param userId the ID of the user
     * @return a ResponseEntity with a CustomApiResponse containing the list of PostDto
     */
    @Operation(summary = "Get Posts by User with Pagination", description = "Retrieve a paginated list of posts by user ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts by user.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomApiResponse<Page<PostDto>>> getPostsByUser(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PostDto> postDtos = postService.getPostsByUser(userId, page, size);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of posts by user.",
                true,
                postDtos), HttpStatus.OK);
    }

    /**
     * Searches for posts by title or content using a keyword.
     * If no matching posts are found, a ResourceNotFoundException is thrown.
     *
     * @param keyword the keyword to search for
     * @return a ResponseEntity with a CustomApiResponse containing the list of PostDto
     */
    @Operation(summary = "Search Posts with Pagination", description = "Search posts by title or content with pagination.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts based on the search keyword.")
    @GetMapping("/search")
    public ResponseEntity<CustomApiResponse<Page<PostDto>>> searchPosts(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PostDto> postDtos = postService.searchPosts(keyword, page, size);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of posts based on search keyword.",
                true,
                postDtos), HttpStatus.OK);
    }

    /**
     * Retrieve a paginated list of posts.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of posts per page (default is 10)
     * @return a ResponseEntity containing a CustomApiResponse with the paginated PostDto list
     */
    @Operation(summary = "Get all Posts with Pagination", description = "Retrieve a paginated list of all posts.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of posts.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<Page<PostDto>>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PostDto> postDtos = postService.getAllPosts(page, size);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of posts.",
                true,
                postDtos), HttpStatus.OK);
    }

    /**
     * Retrieve a post by their unique ID using the provided PostDto.
     *
     * @param id the ID of the post to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the PostDto and
     * an HTTP status of OK
     */
    @Operation(summary = "Get Post by ID", description = "Retrieve a post by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the post.")
    @ApiResponse(responseCode = "404", description = "Post not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<PostDto>> getPostById(@PathVariable Long id) {
        PostDto postDto = postService.getPostById(id)
                .orElseThrow(() -> new PostException("Post not found"));
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the post.",
                true,
                postDto), HttpStatus.OK);
    }

    /**
     * Creates a new post.
     *
     * @param postDto the DTO containing the post information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved post data
     */
    @Operation(summary = "Create a new Post", description = "Create a new post record.")
    @ApiResponse(responseCode = "201", description = "Post created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<PostDto>> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto savedPost = postService.createPost(postDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Post created successfully",
                true,
                savedPost), HttpStatus.CREATED);
    }

    /**
     * Update the details of an existing post using the provided PostDto.
     *
     * @param id      the ID of the post to be updated
     * @param postDto the DTO containing updated post details
     * @return a ResponseEntity containing a CustomApiResponse with the updated PostDto
     */
    @Operation(summary = "Update post", description = "Update the details of an existing post.")
    @ApiResponse(responseCode = "200", description = "Post updated successfully")
    @ApiResponse(responseCode = "404", description = "Post not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<PostDto>> updatePost(
            @PathVariable Long id,
            @RequestBody PostDto postDto) {
        PostDto updatedPost = postService.updatePost(id, postDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Post updated successfully",
                true,
                updatedPost), HttpStatus.OK);
    }

    /**
     * Delete a post by their ID.
     *
     * @param id the ID of the post to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation
     */
    @Operation(summary = "Delete Post", description = "Delete a post by its ID.")
    @ApiResponse(responseCode = "204", description = "Post deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Post not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Post deleted successfully.",
                true,
                null), HttpStatus.NO_CONTENT);
    }
}
