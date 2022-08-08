package com.posterr.services.post;

import com.posterr.exception.BusinessException;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import com.posterr.models.entities.Post;

public interface PostService {
    CreatePostResponseDTO createPost(CreatePostRequestDTO createPostRequestDTO) throws BusinessException;


    void validatePostCreation(Post post) throws BusinessException;

    void validateRepostCreation(Post post) throws BusinessException;

    void validateQuotePostCreation(Post post) throws BusinessException;
}
