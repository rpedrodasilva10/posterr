package com.posterr.repositories;

import com.posterr.models.entities.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
@Slf4j
public class InMemoryRepository implements PostRepository {
    List<Post> postsTable = new ArrayList<>();

    @Override
    public Post save(Post post) {
        log.info("Saving using InMemory Repo");
        Post treatedPost = new Post(generateId(), post.getContent(), post.getUserId(), post.getType(), LocalDateTime.now());
        postsTable.add(post);
        return treatedPost;
    }

    private Long generateId() {
        return ThreadLocalRandom.current().nextLong(1, 1000 + 1);
    }
}
