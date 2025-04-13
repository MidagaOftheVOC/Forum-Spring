package app.web.dto;

import app.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO solely used for rendering purposes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithAvatar {

    private User user;

    private String avatarUrl;

}
