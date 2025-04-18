package app.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotNull(message = "Username mandatory")
    @Size(min = 8, max = 30, message = "Потребителските имена трябва да са между 8 и 30 символа")
    private String username;

    @NotNull(message = "Password mandatory")
    @Size(min = 8, max = 30, message = "Паролите трябва да са между 8 и 30 символа")
    private String rawPassword;

    /**
     * IMPORTANT RULE:
     * While the form CAN take 0 length strings from the web,
     * empty strings MUST be turned to null before drawing up
     * an entry in DB.
     */
    @Size(min = 0, max = 30, message = "Показаното име може да е дълго до 30 символа")
    private String shownUsername;

    @NotNull(message = "Е-пощата е задължителна.")
    @Size(max = 320, message = "Неправилна е-поща.")    //  Max length according to RFC standard
    private String email;

    public boolean hasAltUsername(){
        return shownUsername != null;
    }
}
