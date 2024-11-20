package uz.developers.postapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.developers.postapp.entity.Category;
import uz.developers.postapp.entity.Post;

import java.util.List;

public interface PostRepository extends BaseRepository<Post,Long> {

    @Query(value = "select count(*) > 0 from posts p where p.title = :title or p.content = :content", nativeQuery = true)
    boolean existsByTitleOrContent(@Param("title") String title, @Param("content") String content);


    @Query(value = "select * from posts p where p.user_id = :userId", nativeQuery = true)
    Page<Post> findByUserId(@Param("userId") Long userId, Pageable pageable);


    @Query(value = "select * from posts p where p.title LIKE %:keyword% OR p.content LIKE %:keyword%", nativeQuery = true)
    Page<Post> searchByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);


}
