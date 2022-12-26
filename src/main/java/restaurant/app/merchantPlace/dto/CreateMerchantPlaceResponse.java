package restaurant.app.merchantPlace.dto;

import lombok.Builder;
import restaurant.app.preference.Preference;
import restaurant.app.user.User;

import java.util.List;
@Builder
public class CreateMerchantPlaceResponse {
    private Long id;
    private String merchantName;
    private User merchantPlaceOwner;
    private List<Preference> preferences;
    private String address;
}
