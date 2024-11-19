package uz.developers.postapp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.developers.postapp.entity.Category;
import uz.developers.postapp.entity.Post;

public interface CategoryRepository extends BaseRepository<Category, Long> {


    // Title exists check
    @Query(value = "select count(*) > 0 from category c where c.title = :title", nativeQuery = true)
    boolean existsByTitle(@Param("title") String title);


    //Page<Post> findByCategoryId(Long categoryId, Pageable pageable);

    //
    //Query to get posts by category ID
    @Query(value = "select * from posts p where p.category_id = :categoryId", nativeQuery = true)
    Page<Post> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

}
