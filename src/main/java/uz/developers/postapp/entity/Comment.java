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
@Table(name = "comment")
@Schema(description = "Comment entity represents a user's comment on a specific blog post.")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the comment",
            example = "1")
    private Long id;

    @Column(name = "content", nullable = false)
    @Schema(description = "Content of the comment made by the user.",
            example = "This is a great post! I learned a lot from it.")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "User who made the comment.",
            example = "User ID: 3")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @Schema(description = "Blog post that this comment is related to.",
            example = "Post ID: 5",
            required = true)
    private Post post;

}
