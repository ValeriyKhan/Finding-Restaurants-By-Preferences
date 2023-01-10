package restaurant.app.merchantPlace.dto;

import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.PreferenceEntity;

import java.util.List;

@Getter
@Setter
public class ChangeMerchantPlace {
    private Long id;
    private String merchantName;
    private List<PreferenceEntity> preferenceEntities;
    private String address;
}
