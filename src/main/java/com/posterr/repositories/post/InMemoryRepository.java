package com.posterr.repositories.post;

import com.posterr.models.entities.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
@Slf4j
@Profile("test")
public class InMemoryRepository implements PostRepository {
    List<Post> postsTable = new ArrayList<>();

    @Override
    public Post save(Post post) {
        log.info("Saving using InMemory Repo");
        post.setId(generateId());
        post.setCreatedAt(LocalDateTime.now());
        postsTable.add(post);
        return post;
    }

    private Long generateId() {
        return ThreadLocalRandom.current().nextLong(1, 1000 + 1);
    }
}
