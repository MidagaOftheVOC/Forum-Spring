package app.web.dto;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @Size(min = 8, message = "Usernames between 8 and 30 symbols.")
    private String username;

    @Size(min = 8, message = "Passwords between 8 and 30 symbols.")
    private String password;

}
