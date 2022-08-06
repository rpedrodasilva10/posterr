package com.posterr.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.repositories.InMemoryRepository;
import com.posterr.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@WebMvcTest(PostServiceImpl.class)
@ComponentScan("com.posterr")
@ExtendWith(SpringExtension.class)
class PostServiceTest {

    CreatePostRequestDTO dummyPost = null;
    PostService serviceUnderTest = null;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InMemoryRepository repository;

    @BeforeEach
    void setUp() {
        this.dummyPost = TestUtils.createDummyPostRequest();
        this.serviceUnderTest = new PostServiceImpl(repository, objectMapper);
    }

    @Test
    @DisplayName("Should create a post successfully")
    void shouldCreatePost() {
        Assertions.assertDoesNotThrow(() -> {
            this.serviceUnderTest.createPost(this.dummyPost);
        });
    }


}