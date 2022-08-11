package com.posterr.repositories.user;

import com.posterr.models.entities.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);

    User save(User user);

}
