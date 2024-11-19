package uz.developers.postapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for Category")
public class CategoryDto {

    @Schema(description = "Unique ID of the category",
            example = "1")
    private Long id;

    @NotNull
    @Size(max = 20, message = "Title must be less than or equal to 20 characters")
    @Schema(description = "Title of the Category.",
            example = "Technology")
    private String title;

    @NotNull
    @Size(max = 40, message = "Description must be less than or equal to 40 characters")
    @Schema(description = "A brief description of the Category.",
            example = "Posts related to technological advancements...")
    private String description;









}
