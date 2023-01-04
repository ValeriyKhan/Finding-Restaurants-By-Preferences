package restaurant.app.langMessage.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateMessageRequest {
    @NotNull(message = "Field messageName should not be empty")
    @Size(max = 25)
    private String messageName;
    @NotNull(message = "Field with ru language should not be empty")
    private String ruMessage;
    @NotNull(message = "Field with uz language should not be empty")
    private String uzMessage;
    @NotNull(message = "Field with en language should not be empty")
    private String enMessage;
}
