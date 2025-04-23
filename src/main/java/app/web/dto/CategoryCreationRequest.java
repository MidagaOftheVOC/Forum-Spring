package app.web.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryCreationRequest {

    @Size(min = 1, max = 20)
    private String name;

    @Size(min = 6, max = 6)
    private String colour;

}
