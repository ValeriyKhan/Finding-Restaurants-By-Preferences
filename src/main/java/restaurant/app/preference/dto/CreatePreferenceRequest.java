package restaurant.app.preference.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreatePreferenceRequest {
    @NotNull(message = "Field should not be empty")
    private String name;
}
