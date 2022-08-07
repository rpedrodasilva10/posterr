package com.posterr.utils;

import com.posterr.enumerations.PostTypeEnum;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.entities.Post;
import com.posterr.models.entities.User;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

public class TestUtils {
    @SneakyThrows
    protected TestUtils() {
        throw new IllegalAccessException("Utility class!");
    }

    public static CreatePostRequestDTO createDummyPostRequest() {
        return CreatePostRequestDTO.of("This is a simple post", 1L, "ORIGINAL");
    }

    public static User createMockUser() {
        return User.of(1L, "MockUser", 123L, LocalDateTime.now());
    }

    public static Post createMockSavedPost() {
        return Post.of(28L, "This is a mock content", createMockUser(), PostTypeEnum.ORIGINAL.toString(), LocalDateTime.now());
    }

}