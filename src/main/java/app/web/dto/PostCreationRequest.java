package app.web.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 *
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreationRequest {

    @Size(min = 1, max = 1024)
    private String content;

}
