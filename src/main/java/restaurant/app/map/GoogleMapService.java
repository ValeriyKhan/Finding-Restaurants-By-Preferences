package restaurant.app.map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.branch.Branch;
import restaurant.app.merchantPlace.branch.BranchRepository;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.preference.Preference;
import restaurant.app.threadLocalSingleton.ThreadLocalSingleton;
import restaurant.app.user.User;
import restaurant.app.user.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GoogleMapService {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final MessageSingleton messageSingleton;

    public ResponseEntity<?> getMerchantsNearbyLocations(double lat, double lng) {
        User user = ThreadLocalSingleton.getUser();
        List<Preference> userPreferenceList = user.getPreferenceList();
        Set<Branch> branchSet = branchRepository.findBranchByPreferenceEntitiesIn(userPreferenceList);

        return null;
    }
}
