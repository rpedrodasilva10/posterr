package com.posterr.services.user;

import com.posterr.models.entities.User;
import com.posterr.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        return userOptional.orElse(null);
    }
}
