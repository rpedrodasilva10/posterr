package com.posterr.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor(staticName = "of")
public class CreatePostRequestDTO {
    @NotBlank(message = "The post content is mandatory")
    private String content;

    @NotNull(message = "User identifier (userId) is mandatory")
    private Long userId;

    @NotBlank(message = "The post type is mandatory")
    @Pattern(message = "Invalid post type! The valid types are: 'ORIGINAL', 'QUOTE' or 'REPOST'", regexp = "ORIGINAL|QUOTE|REPOST")
    private String type;

}
