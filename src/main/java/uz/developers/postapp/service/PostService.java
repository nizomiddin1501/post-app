package uz.developers.postapp.service;
import org.springframework.data.domain.Page;
import uz.developers.postapp.exceptions.ResourceNotFoundException;
import uz.developers.postapp.payload.PostDto;
import java.util.List;
import java.util.Optional;

public interface PostService {



    //get all posts by category
    Page<PostDto> getPostsByCategory(Long categoryId, int page, int size);

    //get all posts by user
    Page<PostDto> getPostsByUser(Long userId, int page, int size);

    //search posts
    Page<PostDto> searchPosts(String keyword, int page, int size);



    Page<PostDto> getAllPosts(int page, int size);

    Optional<PostDto> getPostById(Long postId);

    PostDto createPost(PostDto postDto);

    PostDto updatePost(Long postId, PostDto postDto);

    void deletePost(Long postId) throws ResourceNotFoundException;




















}
