package com.posterr.repositories.post;

import com.posterr.models.entities.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Long countTodayUsersPosts(Long userId);

    Optional<Post> findById(Long postId);
    
    List<Post> findAllByUserId(Long userId, Pageable pageable);
}
