package com.posterr.services;

import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@WebMvcTest(PostServiceImpl.class)
@ComponentScan("com.posterr")
@ExtendWith(SpringExtension.class)
class PostServiceTest {

    CreatePostRequestDTO dummyPost = null;
    PostService serviceUnderTest = null;

    @BeforeEach
    void setUp() {
        CreatePostRequestDTO.of("This is a simple post", 1L, LocalDateTime.now());
        this.serviceUnderTest = new PostServiceImpl();
    }

    @Test
    @DisplayName("Should create a post successfully")
    void shouldCreatePost() {

        Assertions.assertDoesNotThrow(() -> {
            CreatePostResponseDTO createdPost = this.serviceUnderTest.createPost(this.dummyPost);
        });
    }
}