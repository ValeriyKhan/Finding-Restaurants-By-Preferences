package restaurant.app.merchantPlace.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetPreferencesToMerchantPlaceRequest {
    private Long merchantPlaceId;
    private List<Long> preferenceIdList;
}
