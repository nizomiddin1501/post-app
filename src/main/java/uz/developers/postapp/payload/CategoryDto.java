package uz.developers.postapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Title of the Category.",
            example = "Technology")
    private String title;

    @Schema(description = "A brief description of the Category.",
            example = "Posts related to technological advancements...")
    private String description;









}
