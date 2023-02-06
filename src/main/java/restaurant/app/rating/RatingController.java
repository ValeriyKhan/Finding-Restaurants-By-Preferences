package restaurant.app.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.app.rating.dto.RateBranchByUserRequest;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<?> rateMerchantPlace(
            @Valid @RequestBody RateBranchByUserRequest rateBranchByUserRequest
    ) {
        return ratingService.rateBranchByUser(rateBranchByUserRequest);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllRatings(
            @RequestParam("size") int size,
            @RequestParam("page") int page
    ) {
        return ratingService.getAllRatings(size, page);
    }

    @GetMapping
    public ResponseEntity<?> getRating(
            @RequestParam("ratingId") Long id
    ){
        return ratingService.getRating(id);
    }
}
