package app.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotNull(message = "Username mandatory")
    @Size(min = 8, max = 30, message = "Username between 8 and 30 chars")
    private String username;

    @NotNull(message = "Password mandatory")
    @Size(min = 8, max = 30, message = "Password between 8 and 30 chars")
    private String rawPassword;

    /**
     * IMPORTANT RULE:
     * While the form CAN take 0 length strings from the web,
     * empty strings MUST be turned to null before drawing up
     * an entry in DB.
     */
    @Size(min = 0, max = 30, message = "Shown username up to 30 characters")
    private String shownUsername;

    @NotNull(message = "E-mail mandatory")
    @Size(max = 320, message = "Bad e-mail entered")    //  Max length according to RFC standard
    private String email;

    public boolean hasAltUsername(){
        return shownUsername != null;
    }
}
