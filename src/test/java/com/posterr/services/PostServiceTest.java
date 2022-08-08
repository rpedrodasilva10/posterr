package com.posterr.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.exception.BusinessException;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.repositories.post.PostRepository;
import com.posterr.services.post.PostService;
import com.posterr.services.post.PostServiceImpl;
import com.posterr.services.user.UserService;
import com.posterr.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserService userService;


    @BeforeEach
    void setUp() {
        this.dummyPost = TestUtils.createDummyPostRequest();
        this.serviceUnderTest = new PostServiceImpl(postRepository, userService, objectMapper);
        Mockito.when(userService.findUserById(1L)).thenReturn(TestUtils.createMockUser());
        Mockito.when(postRepository.save(Mockito.any())).thenReturn(TestUtils.createMockSavedPost());
    }

    @Test
    @DisplayName("Should create a post successfully")
    void shouldCreatePost() {
        Assertions.assertDoesNotThrow(() -> {
            this.serviceUnderTest.createPost(this.dummyPost);
        });
    }

    @Test
    @DisplayName("Should throw when create a post with invalid user id")
    void shouldFailWhenCreatePostWithInvalidUser() {
        Mockito.when(userService.findUserById(Mockito.any())).thenReturn(null);
        Assertions.assertThrows(BusinessException.class, () -> this.serviceUnderTest.createPost(this.dummyPost));
    }

    @Test
    @DisplayName("Should throw if the maximum posts per day was already achieved")
    void shouldFailWhenCreatingPostsWithMaxPerDayAchieved() {
        Mockito.when(postRepository.countTodayUsersPosts(Mockito.any())).thenReturn(6L);
        Assertions.assertThrows(BusinessException.class, () -> this.serviceUnderTest.createPost(this.dummyPost));
    }

    @Test
    @DisplayName("Should pass if the maximum posts per day was not achieved")
    void shouldFailWhenCreatingPostsWithMaxPerDayNotAchieved() {
        Mockito.when(postRepository.countTodayUsersPosts(Mockito.any())).thenReturn(3L);
        Assertions.assertDoesNotThrow(() -> this.serviceUnderTest.createPost(this.dummyPost));
    }


}