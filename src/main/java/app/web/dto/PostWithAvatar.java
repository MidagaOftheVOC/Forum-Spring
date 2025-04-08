package app.web.dto;


import app.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO solely used for rendering purposes.
 */
@Data
@AllArgsConstructor
public class PostWithAvatar {

    Post post;

    String avatarUrl;

}
