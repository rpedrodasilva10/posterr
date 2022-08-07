package com.posterr.services.user;

import com.posterr.exception.ApiException;
import com.posterr.models.entities.User;

public interface UserService {
    User findUserById(Long userId) throws ApiException;
}
