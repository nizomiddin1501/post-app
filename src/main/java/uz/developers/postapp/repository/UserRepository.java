package uz.developers.postapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.developers.postapp.entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {


    @Query(value = "select count(*) > 0 from users u where u.email = :email", nativeQuery = true)
    boolean existsByEmail(@Param("email") String email);


    Optional<User> findByEmail(String email);
}
