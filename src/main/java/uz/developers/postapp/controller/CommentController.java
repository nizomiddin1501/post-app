package uz.developers.postapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.developers.postapp.exceptions.CommentException;
import uz.developers.postapp.payload.CommentDto;
import uz.developers.postapp.payload.CustomApiResponse;
import uz.developers.postapp.service.CommentService;

/**
 * REST controller for managing comments, offering endpoints for
 * creating, updating, retrieving, and deleting comment records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {


    private final CommentService commentService;


    /**
     * Retrieve a list of all comments for a specific post.
     * <p>
     * This method fetches all comments associated with a given post ID and returns them as a list of CommentDto.
     *
     * @param postId the ID of the post for which comments are being retrieved
     * @return a ResponseEntity containing a CustomApiResponse with the list of CommentDto representing all comments for the specified post
     */
    @Operation(summary = "Get all Comments by Post ID", description = "Retrieve a list of all comments for specific post.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of comments for specific post.")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<CustomApiResponse<Page<CommentDto>>> getAllCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<CommentDto> commentDtos = commentService.getAllCommentsByPostId(postId, page, size);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of comments.",
                true,
                commentDtos), HttpStatus.OK);
    }


    /**
     * Retrieve a user by their unique ID using the provided CommentDto.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the CommentDto and
     * an HTTP status of OK
     */
    @Operation(summary = "Get Comment by ID", description = "Retrieve a user by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the comment.")
    @ApiResponse(responseCode = "404", description = "Comment not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CommentDto>> getCommentById(@PathVariable Long id) {
        CommentDto commentDto = commentService.getCommentById(id)
                .orElseThrow(() -> new CommentException("Comment not found"));
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the comment.",
                true,
                commentDto), HttpStatus.OK);
    }


    /**
     * Creates a new comment for a specific post.
     *
     * @param postId     the ID of the post the comment belongs to
     * @param commentDto the DTO with comment information
     * @return ResponseEntity with a CustomApiResponse of the saved comment
     */
    @Operation(summary = "Create a new Comment", description = "Create a new comment record for specific post.")
    @ApiResponse(responseCode = "201", description = "User created successfully.")
    @PostMapping("/posts/{postId}")
    public ResponseEntity<CustomApiResponse<CommentDto>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentDto commentDto) {
        CommentDto savedComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Comment created successfully for post ID: " + postId,
                true,
                savedComment), HttpStatus.CREATED);
    }


    /**
     * Update the details of an existing comment using the provided UserDto.
     *
     * @param id         the ID of the comment to be updated
     * @param commentDto the DTO containing updated comment details
     * @return a ResponseEntity containing a CustomApiResponse with the updated CommentDto
     */
    @Operation(summary = "Update comment", description = "Update the details of an existing user.")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CommentDto>> updateComment(
            @PathVariable Long id,
            @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(id, commentDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Comment updated successfully",
                true,
                updatedComment), HttpStatus.OK);
    }

    /**
     * Delete a comment by their ID.
     *
     * @param id the ID of the comment to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation
     */
    @Operation(summary = "Delete Comment", description = "Delete a comment by its ID.")
    @ApiResponse(responseCode = "204", description = "Comment deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Comment not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Comment deleted successfully.",
                true,
                null), HttpStatus.NO_CONTENT);
    }
}
