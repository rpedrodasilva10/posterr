package com.posterr.repositories.post;

import com.posterr.models.entities.Post;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface JpaPostRepository extends PostRepository, JpaRepository<Post, Long> {
}
