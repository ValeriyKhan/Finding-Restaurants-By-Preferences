package restaurant.app.merchantPlace.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.Preference;

import java.util.List;

@Getter
@Setter
@Builder
public class SetPreferencesToMerchantPlaceResponse {
    private Long merchantPlaceId;
    private String merchantPlaceName;
    private List<Preference> preferenceListOfMerchantPlace;
}
