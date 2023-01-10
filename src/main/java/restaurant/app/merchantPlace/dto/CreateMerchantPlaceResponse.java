package restaurant.app.merchantPlace.dto;

import lombok.Builder;
import restaurant.app.preference.PreferenceEntity;

import java.util.List;
@Builder
public class CreateMerchantPlaceResponse {
    public Long id;
    public String merchantName;
    public String merchantPlaceOwner;
    public String address;
    public List<PreferenceEntity> preferenceEntities;
}
