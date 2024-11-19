package uz.developers.postapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for Post")
public class PostDto {

    @Schema(description = "Unique ID of the post",
            example = "1")
    private Long id;

    @NotNull
    @Size(max = 100, message = "Title must be less than or equal to 100 characters")
    @Schema(description = "Title of the blog post",
            example = "Dependency Injection in Spring")
    private String title;

    @NotNull
    @Schema(description = "Content of the blog post",
            example = "In this post, we will explore the concept of Dependency Injection...")
    private String content;


    @Schema(description = "Date when the post was created",
            example = "2024-01-01")
    private Date date;

    @Schema(description = "Image associated with the blog post",
            example = "http://example.com/image.jpg")
    private String image;

    @Schema(description = "Category ID for the post",
            example = "3",
            required = true)
    private Long categoryId;

    @Schema(description = "User ID for the post",
            example = "7",
            required = true)
    private Long userId;
}
