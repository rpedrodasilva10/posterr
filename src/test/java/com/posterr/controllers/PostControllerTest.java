package com.posterr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.repositories.user.UserRepository;
import com.posterr.services.post.PostServiceImpl;
import com.posterr.utils.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostController.class)
@ComponentScan(value = "com.posterr")
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    final String API_BASE_PATH = "/api/v1";

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PostServiceImpl service;

    @MockBean
    private UserRepository userRepository;

    CreatePostRequestDTO dummyPost = null;

    @BeforeEach
    void setUp() {
        this.dummyPost = TestUtils.createDummyPostRequest();
    }

    @SneakyThrows
    @DisplayName("Should return 400, when missing required field(s)")
    @ValueSource(strings = {"Content", "UserId", "Type"})
    @ParameterizedTest(name = "{index} - {0}")
    void shouldReturnBadRequestForMissingRequiredFields(String field) {
        switch (field) {
            case "Content":
                this.dummyPost.setContent(null);
                break;
            case "UserId":
                this.dummyPost.setUserId(null);
                break;
            case "Type":
                this.dummyPost.setType(null);
                break;
            default:
                Assertions.fail("Field '" + field + "' not found'");
        }

        mockMvc.perform(MockMvcRequestBuilders
                        .post(API_BASE_PATH + "/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.dummyPost))
                )
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @DisplayName("Should return 400, when content doesn't respect max size limits ")
    @SneakyThrows
    void shouldReturnBadRequestForContentOutsideMaxLimits() {
        this.dummyPost.setContent(TestUtils.getContentOutSideLimit());
        mockMvc.perform(MockMvcRequestBuilders
                        .post(API_BASE_PATH + "/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.dummyPost))
                )
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @DisplayName("Should return 400, when content doesn't respect the min size limits ")
    @SneakyThrows
    void shouldReturnBadRequestForContentOutsideMinLimits() {
        this.dummyPost.setContent("");
        mockMvc.perform(MockMvcRequestBuilders
                        .post(API_BASE_PATH + "/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.dummyPost))
                )
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @DisplayName("Should return 201 given valid payload")
    @SneakyThrows
    void shouldPassGivenValidPayload() {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(API_BASE_PATH + "/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.dummyPost))
                )
                .andExpect(status().isCreated()).andDo(print());
    }
}