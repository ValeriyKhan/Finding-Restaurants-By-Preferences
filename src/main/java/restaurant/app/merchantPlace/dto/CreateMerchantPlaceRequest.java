package restaurant.app.merchantPlace.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import restaurant.app.preference.Preference;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateMerchantPlaceRequest {
    @NotNull(message = "Cannot be empty")
    @Size(min = 1, max = 25)
    private String merchantName;
    @NotNull(message = "Cannot be empty")
    private String address;
}
