package com.posterr.utils;

import com.posterr.enumerations.PostTypeEnum;
import com.posterr.models.dto.CreatePostRequestDTO;
import com.posterr.models.entities.Post;
import com.posterr.models.entities.User;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

public class TestUtils {
    @SneakyThrows
    protected TestUtils() {
        throw new IllegalAccessException("Utility class!");
    }

    public static CreatePostRequestDTO createDummyPostRequest() {
        return CreatePostRequestDTO.of("This is a simple post", 1L, "ORIGINAL", null);
    }

    public static User createMockUser() {
        return User.of(1L, "MockUser", 123L, LocalDateTime.now());
    }

    public static Post createMockSavedPost() {
        return Post.of(28L, "This is a mock content", createMockUser(), PostTypeEnum.ORIGINAL.toString(), LocalDateTime.now(), null);
    }

    public static String getContentOutSideLimit() {
        return "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.";
    }

}