package com.posterr.models.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private final Long id;
    private final String content;
    private final Long userId;
    private final LocalDateTime postedAt;
}
