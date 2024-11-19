package uz.developers.postapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.developers.postapp.entity.Post;
import uz.developers.postapp.exceptions.PostException;
import uz.developers.postapp.exceptions.ResourceNotFoundException;
import uz.developers.postapp.payload.PostDto;
import uz.developers.postapp.repository.PostRepository;
import uz.developers.postapp.service.PostService;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;

    private final PostRepository postRepository;


    //get all posts by category
    @Override
    public Page<PostDto> getPostsByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.findByCategoryId(categoryId, pageable);
        if (postsPage.isEmpty()) {
            throw new ResourceNotFoundException("Posts", "Category ID", categoryId);
        }
        return postsPage.map(this::postToDto);
    }


    //get all posts by user
    @Override
    public Page<PostDto> getPostsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.findByUserId(userId, pageable);
        if (postsPage.isEmpty()) {
            throw new ResourceNotFoundException("Posts", "User ID", userId);
        }
        return postsPage.map(this::postToDto);
    }

    //search posts
    @Override
    public Page<PostDto> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.searchByTitleOrContent(keyword, pageable);
        if (postsPage.isEmpty()) {
            throw new ResourceNotFoundException("Posts", "Keyword", keyword.hashCode());
        }
        return postsPage.map(this::postToDto);
    }




    @Override
    public Page<PostDto> getAllPosts(int page, int size) {
        Page<Post> postsPage = postRepository.findAll(PageRequest.of(page, size));
        return postsPage.map(this::postToDto);
    }

    @Override
    public Optional<PostDto> getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return Optional.of(postToDto(post));
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = dtoToPost(postDto);
        if (post.getTitle() == null || post.getContent() == null) {
            throw new PostException("Post title content must not be null");
        }
        boolean exists = postRepository.existsByTitleOrContent(post.getTitle(), post.getContent());
        if (exists) {
            throw new PostException("Post with this title name and content already exists");
        }
        Post savedPost = postRepository.save(post);
        return postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        Post postDetails = dtoToPost(postDto);

        existingPost.setTitle(postDetails.getTitle());
        existingPost.setContent(postDetails.getContent());
        existingPost.setImage(postDetails.getImage());
        existingPost.setDate(postDetails.getDate());

        Post updatedPost = postRepository.save(existingPost);
        return postToDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }





    // DTO ---> Entity
    private Post dtoToPost(PostDto postDto){
        return modelMapper.map(postDto, Post.class);
    }


    // Entity ---> DTO
    public PostDto postToDto(Post post){
        return modelMapper.map(post, PostDto.class);
    }







}
