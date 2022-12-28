package restaurant.app.merchantPlace.dto;

import lombok.Builder;
import restaurant.app.preference.Preference;
import restaurant.app.user.User;

import java.util.List;
@Builder
public class CreateMerchantPlaceResponse {
    public Long id;
    public String merchantName;
    public String merchantPlaceOwner;
    public String address;
    public List<Preference> preferences;
}
