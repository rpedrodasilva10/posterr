package com.posterr.services.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.exception.ApiException;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import com.posterr.models.entities.Post;
import com.posterr.models.entities.User;
import com.posterr.repositories.post.PostRepository;
import com.posterr.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public CreatePostResponseDTO createPost(CreatePostRequestDTO createPostRequestDTO) throws ApiException {
        log.info("[createClient] :: Creating Post... Request: {}",
                objectMapper.convertValue(createPostRequestDTO, JsonNode.class));
        Optional<User> userOpt = this.userRepository.findById(createPostRequestDTO.getUserId());

        if (!userOpt.isPresent()) {
            throw new ApiException(-2, "User not found", "Could not find the user with the given id");
        }

        Post newPost = objectMapper.convertValue(createPostRequestDTO, Post.class);
        newPost.setUser(userOpt.get());

        CreatePostResponseDTO created = CreatePostResponseDTO.of(this.postRepository.save(newPost).getId());
        log.info("[createPost] :: Successfully created post ID: {}", created.getId());
        return created;

    }

}