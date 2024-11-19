package uz.developers.postapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.developers.postapp.entity.Post;
import uz.developers.postapp.entity.User;

public interface UserRepository extends BaseRepository<User, Long> {


    // Name exists check
    @Query(value = "select count(*) > 0 from users u where u.name = :name", nativeQuery = true)
    boolean existsByName(@Param("name") String name);

    // Email exists check
    @Query(value = "select count(*) > 0 from users u where u.email = :email", nativeQuery = true)
    boolean existsByEmail(@Param("email") String email);

    Page<Post> findByUserId(Long userId, Pageable pageable);


}
