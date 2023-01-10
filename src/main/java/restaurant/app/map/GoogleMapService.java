package restaurant.app.map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.MerchantPlace;
import restaurant.app.merchantPlace.MerchantPlaceRepository;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.preference.PreferenceEntity;
import restaurant.app.threadLocalSingleton.ThreadLocalSingleton;
import restaurant.app.user.User;
import restaurant.app.user.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GoogleMapService {

    private final UserRepository userRepository;
    private final MerchantPlaceRepository merchantPlaceRepository;
    private final MessageSingleton messageSingleton;

    public ResponseEntity<?> getMerchantsNearbyLocations(double lat, double lng) {
        User user = ThreadLocalSingleton.getUser();
        List<PreferenceEntity> userPreferenceEntityList = user.getPreferenceEntityList();
        Set<MerchantPlace> merchantPlaceSet = merchantPlaceRepository.findMerchantPlacesByPreferenceEntitiesIn(userPreferenceEntityList);

        return null;
    }
}
