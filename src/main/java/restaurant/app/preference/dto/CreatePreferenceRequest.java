package restaurant.app.preference.dto;

import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.PreferenceE;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreatePreferenceRequest {
    @NotNull(message = "Field should not be empty")
    @Enumerated(EnumType.STRING)
    private PreferenceE preferenceE;
}
