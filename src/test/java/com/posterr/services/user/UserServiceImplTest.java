package com.posterr.services.user;

import com.posterr.models.entities.User;
import com.posterr.repositories.post.PostRepository;
import com.posterr.repositories.user.UserRepository;
import com.posterr.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

@WebMvcTest(UserServiceImpl.class)
@ComponentScan("com.posterr")
class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    PostRepository postRepository;

    UserService serviceUnderTest = null;

    @BeforeEach
    void setUp() {
        this.serviceUnderTest = new UserServiceImpl(userRepository);
    }


    @Test
    @DisplayName("Should return user with success")
    void shouldReturnUser() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(TestUtils.createMockUser()));
        User user = this.serviceUnderTest.findUserById(1L);

        Assertions.assertNotNull(user);
    }
}