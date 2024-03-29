package com.posterr.controllers;

import com.posterr.exception.BusinessException;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import com.posterr.models.entities.Post;
import com.posterr.services.post.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@RequestMapping(value = "/api/v1/posts")
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreatePostResponseDTO createSinglePost(@RequestBody @Valid CreatePostRequestDTO postRequestDTO) throws BusinessException {
        return postService.createPost(postRequestDTO);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Post> getPosts(
            @RequestParam(required = false, defaultValue = "0") Integer skip,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long userId) throws BusinessException {
        return postService.getPosts(userId, skip, limit, startDate, endDate);
    }

}
