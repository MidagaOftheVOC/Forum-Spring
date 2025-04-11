package app.web.dto;

import jakarta.validation.constraints.Size;

public class ThreadCreationRequest {

    @Size(min = 1, max = 255)
    private String title;

    @Size(min = 1, max = 1024)
    private String body;

}
