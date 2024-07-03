package com.springboot.blog.payload;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private long id;
    //title should not be null or empty
    //title should have atleast 2 characters
    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @Size(min = 2, message = "Title should have at least 2 characters")
    private String title;
    //post description should not be null or empty
    //post description should have atleast 10 characters
    @Schema(
            description = "Blog Post description"
    )
    @NotEmpty
    @Size(min=10, message = "Description should have at least 10 characters")
    private String description;

    //post content should not be null or empty
    @Schema(
            description = "Blog Post Content"
    )
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
    @Schema(
            description ="Post Category"
    )


    private Long categoryId;



}
