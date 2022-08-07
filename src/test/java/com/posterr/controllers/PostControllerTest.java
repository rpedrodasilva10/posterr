package com.posterr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.services.post.PostServiceImpl;
import com.posterr.utils.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostController.class)
@ComponentScan(value = "com.posterr")
@ExtendWith(SpringExtension.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    private final String API_BASE_PATH = "/api/v1";

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PostServiceImpl service;

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
}