package restaurant.app.langMessage.dto;

import lombok.Getter;
import lombok.Setter;
import restaurant.app.langMessage.LanguageEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateMessageRequest {
    @NotNull(message = "Field key should not be empty")
    @Size(max = 25)
    private String key;
    @NotNull(message = "Field with message should not be empty")
    private String message;
    @NotNull(message = "Field with language should not be empty")
    private LanguageEnum lang;
}
