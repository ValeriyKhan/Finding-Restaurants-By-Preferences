package restaurant.app.map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/geo")
@RequiredArgsConstructor
public class GoogleMapController {
    private final GoogleMapService googleMapService;
    @GetMapping
    public ResponseEntity<?> getMerchantsNearbyLocations(
            @RequestParam("lat") Long lat,
            @RequestParam("lng") Long lng
    ) {
        return googleMapService.getMerchantsNearbyLocations(lat, lng);
    }
}
