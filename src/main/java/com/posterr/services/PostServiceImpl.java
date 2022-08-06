package com.posterr.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import com.posterr.models.entities.Post;
import com.posterr.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public CreatePostResponseDTO createPost(CreatePostRequestDTO createPostRequestDTO) {
        log.info("[createPost] :: Creating post");
        Post newPost = objectMapper.convertValue(createPostRequestDTO, Post.class);
        
        CreatePostResponseDTO created = CreatePostResponseDTO.of(this.repository.savePost(newPost));
        log.info("[createPost] :: Successfully created post ID: {}", created.getId());
        return created;

    }

}
