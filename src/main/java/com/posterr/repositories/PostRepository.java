package com.posterr.repositories;

import com.posterr.models.entities.Post;

public interface PostRepository {
    Post save(Post post);
}