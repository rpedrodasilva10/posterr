package com.posterr.models.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class CreatePostRequestDTO {
    private final String content;
    private final Long userId;
}
