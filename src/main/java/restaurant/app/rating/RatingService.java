package restaurant.app.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.MerchantPlace;
import restaurant.app.merchantPlace.MerchantPlaceRepository;
import restaurant.app.merchantPlace.branch.Branch;
import restaurant.app.merchantPlace.branch.BranchRepository;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.rating.dto.GetRatingResponse;
import restaurant.app.rating.dto.RateBranchByUserRequest;
import restaurant.app.rating.dto.RateBranchResponse;
import restaurant.app.user.User;
import restaurant.app.user.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final MerchantPlaceRepository merchantPlaceRepository;
    private final MessageSingleton messageSingleton;

    public ResponseEntity<?> rateBranchByUser(RateBranchByUserRequest rateBranchByUserRequest) {
        Optional<User> optionalUser = userRepository.findById(rateBranchByUserRequest.getUserId());
        if (optionalUser.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        Optional<Branch> optionalBranch = branchRepository.findById(rateBranchByUserRequest.getBranchId());
        if (optionalBranch.isEmpty()) {
            return messageSingleton.branchNotFound();
        }
        User user = optionalUser.get();
        Branch branch = optionalBranch.get();
        Optional<Rating> optionalRating = ratingRepository
                .findByUserAndBranch(user, branch);
        Rating rating;
        if (optionalRating.isPresent()) {
            rating = optionalRating.get();
            rating.setScore(rateBranchByUserRequest.getRating());
        } else {
            rating = Rating.builder()
                    .user(user)
                    .branch(branch)
                    .score(rateBranchByUserRequest.getRating())
                    .build();
        }
        ratingRepository.save(rating);
        calculateBranchOverallRating(branch);
        calculateMerchantPlaceOverallRating(branch.getMerchantPlace());
        RateBranchResponse response = RateBranchResponse.builder()
                .branchId(branch.getId())
                .userId(user.getId())
                .score(rateBranchByUserRequest.getRating())
                .build();
        return messageSingleton.ok(Map.of("message", response));
    }

    public void calculateBranchOverallRating(Branch branch) {
        List<Rating> ratingListByBranch = ratingRepository.findByBranch(branch);
        double sumOfScores = ratingListByBranch.stream()
                .map(Rating::getScore)
                .reduce(0, Integer::sum);
        double overallRating = sumOfScores / ratingListByBranch.size();
        branch.setOverallScore(overallRating);
        branchRepository.save(branch);
    }

    public void calculateMerchantPlaceOverallRating(MerchantPlace merchantPlace) {
        double sumOfScores = merchantPlace.getBranches()
                .stream()
                .map(Branch::getOverallScore)
                .reduce(0.0, Double::sum);
        double overallRating = sumOfScores / merchantPlace.getBranches().size();
        merchantPlace.setOverallRating(overallRating);
        merchantPlaceRepository.save(merchantPlace);
    }

    public ResponseEntity<?> getAllRatings(int size, int page) {
        Page<Rating> ratingPage = ratingRepository.findAll(PageRequest.of(page, size));
        List<GetRatingResponse> response = ratingPage.stream()
                .map(r -> GetRatingResponse.builder()
                        .ratingId(r.getId())
                        .userId(r.getUser().getId())
                        .branchId(r.getBranch().getId())
                        .score(r.getScore())
                        .build())
                .toList();
        return messageSingleton.ok(Map.of("ratings", response));
    }

    public ResponseEntity<?> getRating(Long id) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isEmpty()) {
            return messageSingleton.ratingDoesNotExist();
        }
        Rating rating = optionalRating.get();
        GetRatingResponse response = GetRatingResponse.builder()
                .ratingId(rating.getId())
                .userId(rating.getUser().getId())
                .branchId(rating.getBranch().getId())
                .score(rating.getScore())
                .build();
        return messageSingleton.ok(Map.of("rating", response));
    }
}
