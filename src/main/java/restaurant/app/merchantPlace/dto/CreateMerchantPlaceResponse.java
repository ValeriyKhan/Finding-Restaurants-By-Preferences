package restaurant.app.merchantPlace.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMerchantPlaceResponse {
    public Long merchantPlaceId;
    public String merchantName;
    public String descriptions;
    public Long userOwnerId;
}
