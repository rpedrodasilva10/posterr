package com.posterr.services.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.posterr.enumerations.PostTypeEnum;
import com.posterr.exception.BusinessException;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.dto.CreatePostResponseDTO;
import com.posterr.models.entities.Post;
import com.posterr.models.entities.User;
import com.posterr.repositories.post.PostRepository;
import com.posterr.services.user.UserService;
import com.posterr.utils.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "User not found", "Could not find the user with the given id");
        }

        Post newPost = createPostRequestDTO.toModel();
        newPost.setUser(foundUser);

        // I only search a base post and define the message if it's not an original post
        if (!PostTypeEnum.ORIGINAL.toString().equals(newPost.getType())) {
            if (Objects.isNull(createPostRequestDTO.getOriginPostId())) {
                throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Invalid base post!", "To create posts with type 'QUOTE' or 'REPOST' is mandatory to inform field 'originPostId'");
            }
            newPost.setOriginPost(this.findPostById(createPostRequestDTO.getOriginPostId()));

        }

        // Checks against rules if is possible to create a new post
        this.validatePostCreation(newPost);


        CreatePostResponseDTO created = CreatePostResponseDTO.of(this.postRepository.save(newPost).getId());
        log.info("[createPost] :: Successfully created post ID: {}", created.getId());
        return created;

    }

    @Override
    public void validatePostCreation(Post post) throws BusinessException {
        final int usersMaxPostsPerDay = 5;
        Long userPostsQuantityFromToday = this.postRepository.countTodayUsersPosts(post.getUser().getId());

        if (userPostsQuantityFromToday >= usersMaxPostsPerDay) {
            throw new BusinessException(HttpStatus.NOT_ACCEPTABLE.value(), "User reached the limit of posts per day", "You can create " + usersMaxPostsPerDay + " posts per day");
        }

        this.validateRepostCreation(post);
        this.validateQuotePostCreation(post);
    }

    @Override
    public void validateRepostCreation(Post post) throws BusinessException {
        // If is not REPOST type, we don't do anything
        if (!post.getType().equals(PostTypeEnum.REPOST.toString()))
            return;

        checkIfBasePostExists(post);
        Post originPost = post.getOriginPost();

        if (originPost.getType().equals(PostTypeEnum.REPOST.toString())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Invalid repost! Original post must be of type 'ORIGINAL' or 'QUOTE'", "Base post invalid type for this operation");
        }
    }


    private void checkIfBasePostExists(Post post) throws BusinessException {
        Post originPost = post.getOriginPost();
        if (Objects.isNull(originPost)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Invalid repost! Original post not found", "Base post not found with the given id");
        }


    }

    private Post findPostById(Long postId) {
        return this.postRepository.findById(postId).orElse(null);
    }

    @Override
    public void validateQuotePostCreation(Post post) throws BusinessException {
        // If is not QUOTE type, we don't do anything
        if (!post.getType().equals(PostTypeEnum.QUOTE.toString()))
            return;

        checkIfBasePostExists(post);
        Post originPost = post.getOriginPost();

        if (originPost.getType().equals(PostTypeEnum.QUOTE.toString())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Invalid repost! Original post must be of type 'ORIGINAL' or 'QUOTE'", "Base post invalid type for this operation");
        }

        // I won't check for quoteMessage not empty because Twitter allow users to quote tweets without a message
    }

    @Override
    public List<Post> getPosts(Long userId, Integer skip, Integer limit) {
        Pageable pageable = new OffsetBasedPageRequest(limit, skip, Sort.by(Sort.Direction.DESC, "id"));
        return this.postRepository.findAllByUserId(userId, pageable);
    }
}
