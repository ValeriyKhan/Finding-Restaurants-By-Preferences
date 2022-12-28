package restaurant.app.merchantPlace;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.app.merchantPlace.dto.ChangeMerchantPlace;
import restaurant.app.merchantPlace.dto.CreateMerchantPlaceRequest;
import restaurant.app.merchantPlace.dto.SetPreferencesToMerchantPlaceRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/merchant-place")
@RequiredArgsConstructor
public class MerchantPlaceController {
    private final MerchantPlaceService merchantPlaceService;

    @PostMapping
    public ResponseEntity<?> createMerchantPlace(
            @Valid @RequestBody CreateMerchantPlaceRequest createMerchantPlaceRequest
    ) {
        return merchantPlaceService.createMerchantPlace(createMerchantPlaceRequest);
    }

    @GetMapping
    public ResponseEntity<?> getMerchantPlace(
            @RequestParam("id") Long id
    ) {
        return merchantPlaceService.getMerchantPlace(id);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllMerchantPlaces(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return merchantPlaceService.getAllMerchantPlaces(page, size);
    }

    @PutMapping
    public ResponseEntity<?> changeMerchantPlace(
            @RequestBody ChangeMerchantPlace changeMerchantPlace
    ) {
        return merchantPlaceService.changeMerchantPlace(changeMerchantPlace);
    }
    @PostMapping("set-preferences")
    public ResponseEntity<?> setPreferencesToMerchantPlace(
            @RequestBody SetPreferencesToMerchantPlaceRequest setPreferencesToMerchantPlaceRequest
    ) {
        return merchantPlaceService.setPreferencesToMerchantPlace(setPreferencesToMerchantPlaceRequest);
    }


}
