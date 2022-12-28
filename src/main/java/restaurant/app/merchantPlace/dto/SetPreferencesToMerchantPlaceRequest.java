package restaurant.app.merchantPlace.dto;

import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.Preference;

import java.util.List;

@Getter
@Setter
public class SetPreferencesToMerchantPlaceRequest {
    private Long merchantPlaceId;
    private List<Preference> preferences;
}
