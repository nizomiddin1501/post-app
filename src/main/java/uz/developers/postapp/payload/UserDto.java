package uz.developers.postapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for User")
public class UserDto {


    @Schema(description = "Unique ID of the user",
            example = "1")
    private Long id;

    @NotNull
    @Size(max = 20, message = "Name must be less than or equal to 20 characters")
    @Schema(description = "Name of the user",
            example = "Nizomiddin Mirzanazarov")
    private String name;

    @NotNull
    @Email(message = "Email should be valid")
    @Schema(description = "Email address of the user",
            example = "nizomiddinmirzanazarov@gmail.com")
    private String email;

    @NotNull
    @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars !!")
    @Schema(description = "Password of the user",
            example = "password123")
    private String password;

    @Schema(description = "Brief description about the user",
            example = "A passionate blogger and tech enthusiast.")
    private String about;


}
