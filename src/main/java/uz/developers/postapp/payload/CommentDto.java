package uz.developers.postapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for Comment")
public class CommentDto {

    @Schema(description = "Unique ID of the comment",
            example = "1")
    private Long id;

    @NotBlank(message = "Content cannot be blank")
    @Schema(description = "Content of the comment made by the user",
            example = "This is a great post! I learned a lot from it.")
    private String content;

    @Schema(description = "User ID made the comment",
            example = "3")
    private Long userId;

    @Schema(description = "Post ID for the comment",
            example = "1")
    private Long postId;











}
