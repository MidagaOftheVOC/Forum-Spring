package app.web.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditingRequest {

    @Size(min = 8, max = 30)
    private String username;

    @Size(min = 0, max = 30, message = "Показаното име може да е дълго до 30 символа.")
    private String shownUsername;

    @Size(max = 255)
    private String quote;

    @Email(message = "Въведена е неправилна е-поща.")
    @NotNull
    private String email;
}
