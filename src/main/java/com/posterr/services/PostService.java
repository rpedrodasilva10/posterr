package com.posterr.services;

import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;

public interface PostService {
    CreatePostResponseDTO createPost(CreatePostRequestDTO createPostRequestDTO) throws Exception;

}
