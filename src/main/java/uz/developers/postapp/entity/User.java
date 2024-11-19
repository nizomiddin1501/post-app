package uz.developers.postapp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "User entity represents a registered user in the blog application.")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the user",
            example = "1")
    private Long id;

    @Column(name = "name", nullable = false)
    @Schema(description = "Name of the user",
            example = "Nizomiddin Mirzanazarov",
            required = true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "Email address of the user",
            example = "nizomiddin@example.com",
            required = true)
    private String email;

    @Column(name = "password", nullable = false)
    @Schema(description = "Password of the user",
            example = "password123",
            required = true)
    private String password;
}