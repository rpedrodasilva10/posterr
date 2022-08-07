package com.posterr.controllers;

import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import com.posterr.services.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@RequestMapping("/api/v1")
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping("/posts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreatePostResponseDTO createSinglePost(@RequestBody @Valid CreatePostRequestDTO postRequestDTO) {
        return postService.createPost(postRequestDTO);
    }

}