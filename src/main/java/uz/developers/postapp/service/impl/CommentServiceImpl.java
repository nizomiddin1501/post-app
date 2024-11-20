package uz.developers.postapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.developers.postapp.entity.Comment;
import uz.developers.postapp.entity.Post;
import uz.developers.postapp.exceptions.CommentException;
import uz.developers.postapp.exceptions.ResourceNotFoundException;
import uz.developers.postapp.exceptions.UserException;
import uz.developers.postapp.payload.CommentDto;
import uz.developers.postapp.repository.CommentRepository;
import uz.developers.postapp.repository.PostRepository;
import uz.developers.postapp.service.CommentService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    @Override
    public Page<CommentDto> getAllCommentsByPostId(Long postId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Comment> commentsPage = commentRepository.findByPostId(postId, pageRequest);
        return commentsPage.map(this::commentToDto);
    }

    @Override
    public Optional<CommentDto> getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        return Optional.of(commentToDto(comment));
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = dtoToComment(commentDto);
             comment.setPost(post);
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new CommentException("Comment content must not be null or empty");
        }
        Comment savedComment = commentRepository.save(comment);
        return commentToDto(savedComment);
    }

    @Override
    public CommentDto updateComment(Long commentId, CommentDto commentDto, Long userId) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!existingComment.getUser().getId().equals(userId)) {
            throw new UserException("You are not authorized to update this comment");
        }
        //Comment commentDetails = dtoToComment(commentDto);
        existingComment.setContent(commentDto.getContent());
        Comment updatedComment = commentRepository.save(existingComment);
        return commentToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getUser().getId().equals(userId)) {
            throw new UserException("You are not authorized to delete this comment");
        }
        commentRepository.delete(comment);
    }

    // DTO to Entity
    private Comment dtoToComment(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }

    // Entity to DTO
    public CommentDto commentToDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }




}
