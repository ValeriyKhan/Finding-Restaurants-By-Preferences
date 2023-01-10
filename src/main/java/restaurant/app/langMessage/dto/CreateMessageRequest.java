package restaurant.app.langMessage.dto;

import lombok.Getter;
import lombok.Setter;
import restaurant.app.langMessage.Language;
import restaurant.app.langMessage.MessageKey;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateMessageRequest {
    @NotNull(message = "Field key should not be empty")
    private MessageKey key;
    @NotNull(message = "Field with message should not be empty")
    private String message;
    @NotNull(message = "Field with language should not be empty")
    private Language lang;
}
