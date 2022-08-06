package com.posterr.utils;

import com.posterr.models.dto.CreatePostRequestDTO;
import lombok.SneakyThrows;

public class TestUtils {
    @SneakyThrows
    protected TestUtils() {
        throw new IllegalAccessException("Utility class!");
    }

    public static CreatePostRequestDTO createDummyPostRequest() {
        return CreatePostRequestDTO.of("This is a simple post", 1L, "ORIGINAL");
    }

}