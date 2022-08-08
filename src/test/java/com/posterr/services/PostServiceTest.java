package com.posterr.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.enumerations.PostTypeEnum;
import com.posterr.exception.BusinessException;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.entities.Post;
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

import java.util.Optional;

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


    @Test
    @DisplayName("Should fail when REPOSTING a post of type 'REPOST'")
    void shouldFailWhenRepostingPostTypeRepost() {
        this.dummyPost.setOriginPostId(2L);
        this.dummyPost.setType(PostTypeEnum.REPOST.toString());

        Post mockPostToReturnOnFind = TestUtils.createMockSavedPost();
        mockPostToReturnOnFind.setContent("This is a repost");
        mockPostToReturnOnFind.setType(PostTypeEnum.REPOST.toString());

        Mockito.when(this.postRepository.findById(Mockito.any())).thenReturn(Optional.of(mockPostToReturnOnFind));

        Assertions.assertThrows(BusinessException.class, () -> this.serviceUnderTest.createPost(this.dummyPost));
    }

    @Test
    @DisplayName("Should create REPOST with success")
    void shouldCreateRepostWithSuccess() {
        this.dummyPost.setOriginPostId(2L);
        this.dummyPost.setType(PostTypeEnum.REPOST.toString());

        Post mockPostToReturnOnFind = TestUtils.createMockSavedPost();
        mockPostToReturnOnFind.setType(PostTypeEnum.ORIGINAL.toString());
        Mockito.when(this.postRepository.findById(Mockito.any())).thenReturn(Optional.of(mockPostToReturnOnFind));

        Assertions.assertDoesNotThrow(() -> this.serviceUnderTest.createPost(this.dummyPost));
    }

    @Test
    @DisplayName("Should fail when QUOTING a post type QUOTE with success")
    void shouldFailWhenQuotingPostTypeQuote() {
        this.dummyPost.setOriginPostId(2L);
        this.dummyPost.setQuoteMessage("This is what truth looks like. Look at this guy's wisdom");
        this.dummyPost.setType(PostTypeEnum.QUOTE.toString());

        Post mockPostToReturnOnFind = TestUtils.createMockSavedPost();
        mockPostToReturnOnFind.setContent("Renan should be hired, he is a very good software engineer");
        mockPostToReturnOnFind.setType(PostTypeEnum.QUOTE.toString());

        Mockito.when(this.postRepository.findById(Mockito.any())).thenReturn(Optional.of(mockPostToReturnOnFind));

        Assertions.assertThrows(BusinessException.class, () -> this.serviceUnderTest.createPost(this.dummyPost));
    }

    @Test
    @DisplayName("Should create QUOTE post with success")
    void shouldCreateQuotePostWithSuccess() {
        this.dummyPost.setOriginPostId(2L);
        this.dummyPost.setQuoteMessage("This is what truth looks like. Look at this guy's wisdom");
        this.dummyPost.setType(PostTypeEnum.QUOTE.toString());

        Post mockPostToReturnOnFind = TestUtils.createMockSavedPost();
        mockPostToReturnOnFind.setContent("Renan should be hired, he is a very good software engineer");

        Mockito.when(this.postRepository.findById(Mockito.any())).thenReturn(Optional.of(mockPostToReturnOnFind));

        Assertions.assertDoesNotThrow(() -> this.serviceUnderTest.createPost(this.dummyPost));
    }


}