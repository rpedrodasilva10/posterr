package com.posterr.services.post;

import com.posterr.exception.BusinessException;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import com.posterr.models.entities.Post;

import java.util.List;

public interface PostService {
    CreatePostResponseDTO createPost(CreatePostRequestDTO createPostRequestDTO) throws BusinessException;

    List<Post> getPosts(Long userId, Integer skip, Integer limit, String startDate, String endDate) throws BusinessException;


    void validatePostCreation(Post post) throws BusinessException;

    void validateRepostCreation(Post post) throws BusinessException;

    void validateQuotePostCreation(Post post) throws BusinessException;
}
