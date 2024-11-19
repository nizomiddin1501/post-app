package uz.developers.postapp.service;
import org.springframework.data.domain.Page;
import uz.developers.postapp.payload.UserDto;
import java.util.Optional;

public interface UserService {

    Page<UserDto> getAllUsers(int page, int size);

    Optional<UserDto> getUserById(Long userId);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);



}
