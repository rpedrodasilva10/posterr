package com.posterr.services.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.exception.BusinessException;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import com.posterr.models.entities.Post;
import com.posterr.models.entities.User;
import com.posterr.repositories.post.PostRepository;
import com.posterr.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    public CreatePostResponseDTO createPost(CreatePostRequestDTO createPostRequestDTO) throws BusinessException {
        log.info("[createClient] :: Creating Post... Request: {}",
                objectMapper.convertValue(createPostRequestDTO, JsonNode.class));
        User foundUser = this.userService.findUserById(createPostRequestDTO.getUserId());

        if (Objects.isNull(foundUser)) {
            throw new BusinessException(-2, "User not found", "Could not find the user with the given id");
        }

        Post newPost = objectMapper.convertValue(createPostRequestDTO, Post.class);
        newPost.setUser(foundUser);

        // Checks against rules if is possible to create a new post
        this.validatePostCreation(newPost);


        CreatePostResponseDTO created = CreatePostResponseDTO.of(this.postRepository.save(newPost).getId());
        log.info("[createPost] :: Successfully created post ID: {}", created.getId());
        return created;

    }

    private void validatePostCreation(Long userId) throws BusinessException {
        final int usersMaxPostsPerDay = 5;
        Long userPostsQuantityFromToday = this.postRepository.countTodayUsersPosts(userId);

        if (userPostsQuantityFromToday >= usersMaxPostsPerDay) {
            throw new BusinessException(HttpStatus.NOT_ACCEPTABLE.value(), "User reached the limit of posts per day", "You can create " + usersMaxPostsPerDay + " posts per day");
        }
    }

}
