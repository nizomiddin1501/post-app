package uz.developers.postapp.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.developers.postapp.entity.Comment;

public interface CommentRepository extends BaseRepository<Comment, Long> {


    // Content exists check
    @Query(value = "select count(*) > 0 from comment c where c.content = :content", nativeQuery = true)
    boolean existsByContent(@Param("content") String content);



    @Query(value = "select * from comment where post_id = :postId", nativeQuery = true)
    Page<Comment> findByPostId(@Param("postId") Long postId, Pageable pageable);



}
