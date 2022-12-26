package restaurant.app.merchantPlace.dto;

import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.Preference;

import java.util.List;

@Getter
@Setter
public class ChangeMerchantPlace {
    private Long id;
    private String merchantName;
    private List<Preference> preferences;
    private String address;
}
