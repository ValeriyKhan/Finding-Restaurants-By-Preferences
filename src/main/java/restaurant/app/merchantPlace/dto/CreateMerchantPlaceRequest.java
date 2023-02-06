package restaurant.app.merchantPlace.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateMerchantPlaceRequest {
    @NotNull
    private String merchantName;
    @NotNull
    private String description;
    @NotNull
    private Long userId;

}
