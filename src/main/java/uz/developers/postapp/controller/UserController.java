package uz.developers.postapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.developers.postapp.exceptions.UserException;
import uz.developers.postapp.payload.CustomApiResponse;
import uz.developers.postapp.payload.LoginRequest;
import uz.developers.postapp.payload.UserDto;
import uz.developers.postapp.service.UserService;

/**
 * REST controller for managing users, offering endpoints for
 * creating, updating, retrieving, and deleting user records.
 */
@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Retrieve a paginated list of users.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of users per page (default is 10)
     * @return a ResponseEntity containing a CustomApiResponse with the paginated UserDto list
     */
    @Operation(summary = "Get all Users with Pagination", description = "Retrieve a paginated list of all users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<Page<UserDto>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<UserDto> userDtos = userService.getAllUsers(page, size);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of users.",
                true,
                userDtos), HttpStatus.OK);
    }


    /**
     * Retrieve a user by their unique ID using the provided UserDto.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the UserDto and
     * an HTTP status of OK
     */
    @Operation(summary = "Get User by ID", description = "Retrieve a user by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the user.")
    @ApiResponse(responseCode = "404", description = "User not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id)
                .orElseThrow(() -> new UserException("User not found"));
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the user.",
                true,
                userDto), HttpStatus.OK);
    }

    /**
     * Authenticate a user with the provided email and password.
     *
     * @param loginRequest the login request containing email and password
     * @return a ResponseEntity with a CustomApiResponse containing the UserDto on successful login
     */
    @Operation(summary = "User Login", description = "Authenticate a user using their email and password.")
    @ApiResponse(responseCode = "200", description = "Login successful.")
    @ApiResponse(responseCode = "401", description = "Invalid credentials or unauthorized access.")
    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse<UserDto>> loginUser(@RequestBody LoginRequest loginRequest) {
        UserDto userDto = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Login successful",
                true,
                userDto), HttpStatus.OK);
    }


    /**
     * Register new user.
     *
     * @param userDto the DTO containing the user information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved user data
     */
    @Operation(summary = "Register a new User", description = "Register a new user record.")
    @ApiResponse(responseCode = "201", description = "User registered successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<UserDto>> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "User registered successfully",
                true,
                savedUser), HttpStatus.CREATED);
    }


    /**
     * Update the details of an existing user using the provided UserDto.
     *
     * @param id      the ID of the user to be updated
     * @param userDto the DTO containing updated user details
     * @return a ResponseEntity containing a CustomApiResponse with the updated UserDto
     */
    @Operation(summary = "Update user", description = "Update the details of an existing user.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UserDto>> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "User updated successfully",
                true,
                updatedUser), HttpStatus.OK);
    }


    /**
     * Delete a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation
     */
    @Operation(summary = "Delete User", description = "Delete a user by its ID.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully.")
    @ApiResponse(responseCode = "404", description = "User not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "User deleted successfully.",
                true,
                null), HttpStatus.NO_CONTENT);
    }
}
