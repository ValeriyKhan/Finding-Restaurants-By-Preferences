package restaurant.app.map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.MerchantPlace;
import restaurant.app.merchantPlace.MerchantPlaceRepository;
import restaurant.app.preference.Preference;
import restaurant.app.rating.Rating;
import restaurant.app.threadLocalSingleton.ThreadLocalSingleton;
import restaurant.app.user.User;
import restaurant.app.user.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GoogleMapService {

    private final UserRepository userRepository;
    private final MerchantPlaceRepository merchantPlaceRepository;

    public ResponseEntity<?> getMerchantsNearbyLocations(Long lat, Long lng) {
        User user = ThreadLocalSingleton.getUser();
        List<Preference> preferences = user.getPreferenceList();
        List<MerchantPlace> merchantPlaces = merchantPlaceRepository.findMerchantPlacesByPreferencesIn(preferences);
        return null;
    }
}
