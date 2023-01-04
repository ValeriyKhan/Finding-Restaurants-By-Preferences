package restaurant.app.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class AddPreferencesToUserRequest {
    @NotNull(message = "ID field should not be empty")
    private Long userId;
    @NotNull(message = "List of preferences should not be empty")
    private List<Long> preferenceIds;
}
