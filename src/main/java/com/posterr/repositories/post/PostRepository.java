package com.posterr.repositories.post;

import com.posterr.models.entities.Post;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Long countTodayUsersPosts(Long userId);

    Optional<Post> findById(Long postId);
}
