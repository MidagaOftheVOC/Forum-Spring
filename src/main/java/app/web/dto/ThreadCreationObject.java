package app.web.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ThreadCreationObject {

    @Size(min = 1, max = 256)
    private String title;

    @Size(max = 1024)
    private String body;

    /*
    Lock and pin status must be dictated from services, not here. By default, they shouldn't be any.
    User metadata must be accessed from elsewhere also.
     */
}
