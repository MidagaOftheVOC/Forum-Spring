package app.web.dto;

import app.user.model.UserStatus;
import app.user.model.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class UserCreationObject {

    private String shownUsername;

    private String quote;

    private UserStatus userStatus;

    private UserType userType;

    private String email;


}
