package restaurant.app.merchantPlace;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.app.merchantPlace.dto.CreateMerchantPlaceRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @GetMapping("{id}")
    public ResponseEntity<?> getMerchantPlace(
           @NotNull @PathVariable("id") Long id
    )
    {
        return merchantPlaceService.getMerchantPlace(id);
    }
    @GetMapping("all")
    public ResponseEntity<?> getAllMerchantPlaces(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return merchantPlaceService.getAllMerchantPlaces(page, size);
    }
    @GetMapping("{id}/branches")
    public ResponseEntity<?> getBranchesFromMerchantPlace(
            @NotNull @PathVariable("id") Long merchantPlaceId
    ){
        return merchantPlaceService.getBranchesFromMerchantPlace(merchantPlaceId);
    }
}
