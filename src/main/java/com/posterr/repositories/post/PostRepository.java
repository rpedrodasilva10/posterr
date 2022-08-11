package com.posterr.repositories.post;

import com.posterr.models.entities.Post;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Long countTodayUsersPosts(Long userId);

    Optional<Post> findById(Long postId);


    List<Post> findAllByCreatedAtBetween(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    List<Post> findByUserIdAndCreatedAtBetween(Long userId, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
}
