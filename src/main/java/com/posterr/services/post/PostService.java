package com.posterr.services.post;

import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;

public interface PostService {
    CreatePostResponseDTO createPost(CreatePostRequestDTO createPostRequestDTO) throws Exception;

}
