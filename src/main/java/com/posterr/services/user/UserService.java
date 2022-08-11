package com.posterr.services.user;

import com.posterr.models.entities.User;

public interface UserService {
    User findUserById(Long userId);

    void incrementPostCount(User user);
}
