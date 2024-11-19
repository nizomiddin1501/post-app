package uz.developers.postapp.service;

import org.springframework.data.domain.Page;
import uz.developers.postapp.exceptions.CommentException;
import uz.developers.postapp.exceptions.ResourceNotFoundException;
import uz.developers.postapp.payload.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Page<CommentDto> getAllCommentsByPostId(Long postId, int page, int size);

    Optional<CommentDto> getCommentById(Long commentId);

    CommentDto createComment(Long postId, CommentDto commentDto);

    CommentDto updateComment(Long commentId, CommentDto commentDto);

    void deleteComment(Long commentId);







}
