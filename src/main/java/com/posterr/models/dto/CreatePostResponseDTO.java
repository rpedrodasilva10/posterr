package com.posterr.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class CreatePostResponseDTO {
    private final Long id;
}
