package com.posterr.repositories.post;

import com.posterr.models.entities.Post;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface JpaPostRepository extends PostRepository, JpaRepository<Post, Long> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) from posts where user_id  = ?1 and created_at\\:\\:date = now()\\:\\:date;")
    Long countTodayUsersPosts(Long userId);

    List<Post> findAllByUserId(Long userId, Pageable pageable);
}
