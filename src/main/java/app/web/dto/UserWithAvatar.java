package app.web.dto;


import app.user.model.User;
import lombok.Data;

/**
 * SOMEONE removed the avatar URL col from the User model. Hasty compromise.
 */
@Data
public class UserWithAvatar {

    private User user;

    private String avatarFile;

}
