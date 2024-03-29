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
        Optional<User> userOptional = this.userRepository.findById(userId);

        return userOptional.orElse(null);
    }

    @Override
    public void incrementPostCount(User user) {
        log.info("[incrementPostCount] :: Incrementing post count for userId: {}, current post count is: {} ", user.getId(), user.getPostCount());
        user.setPostCount(user.getPostCount() + 1);
        this.userRepository.save(user);
        log.info("[incrementPostCount] :: Incremented! userId: {}, new post count is: {} ", user.getId(), user.getPostCount());
    }
}
