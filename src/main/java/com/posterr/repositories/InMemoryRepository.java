package com.posterr.repositories;

import com.posterr.models.entities.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class InMemoryRepository implements PostRepository {
    List<Post> postsTable = new ArrayList<>();

    @Override
    public Long savePost(Post post) {
        Post treatedPost = new Post(generateId(), post.getContent(), post.getUserId(), LocalDateTime.now());
        postsTable.add(post);
        return treatedPost.getId();
    }

    private Long generateId() {
        return ThreadLocalRandom.current().nextLong(1, 1000 + 1);
    }
}
