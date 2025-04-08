package app.web.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 *
 *
 */
@Data
@Builder
public class PostCreationRequest {

    int threadIdWherePosted;

    String content;

}
