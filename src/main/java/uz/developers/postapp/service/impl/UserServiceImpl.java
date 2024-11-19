package uz.developers.postapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.developers.postapp.entity.User;
import uz.developers.postapp.exceptions.ResourceNotFoundException;
import uz.developers.postapp.exceptions.UserException;
import uz.developers.postapp.payload.UserDto;
import uz.developers.postapp.repository.UserRepository;
import uz.developers.postapp.service.UserService;
import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public Page<UserDto> getAllUsers(int page, int size) {
        Page<User> usersPage = userRepository.findAll(PageRequest.of(page, size));
        return usersPage.map(this::userToDto);
    }

    @Override
    public Optional<UserDto> getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        return Optional.of(userToDto(user));
    }
    // Login
    @Override
    public UserDto loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found with this email"));
        if (!matches(password, user.getPassword())) {
            throw new UserException("Invalid credentials");
        }
        return userToDto(user);
    }

    // Register
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        if (user.getName() == null || user.getEmail() == null){
            throw new UserException("User name and email must not be null");
        }
        boolean exists = userRepository.existsByEmail(user.getEmail());
        if (exists) {
            throw new UserException("User with this email already exists");
        }
        User savedUser = userRepository.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        User userDetails = dtoToUser(userDto);
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPassword(userDetails.getPassword());
        User updatedUser = userRepository.save(existingUser);
        return userToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        userRepository.delete(user);
    }

    // DTO ---> Entity
    private User dtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    // Entity ---> DTO
    public UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
