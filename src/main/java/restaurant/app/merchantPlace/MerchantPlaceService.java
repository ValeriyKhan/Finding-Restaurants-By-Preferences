package restaurant.app.merchantPlace;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.branch.Branch;
import restaurant.app.merchantPlace.branch.dto.GetAllBranchesResponse;
import restaurant.app.merchantPlace.dto.*;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.role.UserRole;
import restaurant.app.threadLocalSingleton.ThreadLocalSingleton;
import restaurant.app.user.User;
import restaurant.app.user.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static restaurant.app.threadLocalSingleton.ThreadLocalSingleton.*;

@Service
@RequiredArgsConstructor
public class MerchantPlaceService {
    private final MerchantPlaceRepository merchantPlaceRepository;
    private final UserRepository userRepository;
    private final MessageSingleton messageSingleton;

    public ResponseEntity<?> createMerchantPlace(CreateMerchantPlaceRequest createMerchantPlaceRequest) {
        Optional<MerchantPlace> merchantPlaceOptional = merchantPlaceRepository.findByMerchantName(createMerchantPlaceRequest.getMerchantName());
        if (merchantPlaceOptional.isPresent()) {
            return messageSingleton.merchantAlreadyExist();
        }
        Optional<User> userOptional = userRepository.findById(createMerchantPlaceRequest.getUserId());
        if (userOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User user = userOptional.get();
        MerchantPlace merchantPlace = MerchantPlace.builder()
                .merchantName(createMerchantPlaceRequest.getMerchantName())
                .description(createMerchantPlaceRequest.getDescription())
                .owner(user)
                .build();
        merchantPlaceRepository.save(merchantPlace);
        CreateMerchantPlaceResponse response = CreateMerchantPlaceResponse.builder()
                .merchantPlaceId(merchantPlace.getId())
                .merchantName(merchantPlace.getMerchantName())
                .descriptions(merchantPlace.getDescription())
                .userOwnerId(merchantPlace.getOwner().getId())
                .build();
        return messageSingleton.ok(Map.of("merchantPlace", response));
    }

    public ResponseEntity<?> getAllMerchantPlaces(int page, int size) {
        Page<MerchantPlace> merchantPlaces = merchantPlaceRepository.findAll(PageRequest.of(page, size));
        List<GetAllMerchantPlaceResponse> response = merchantPlaces.stream()
                .map(m -> GetAllMerchantPlaceResponse.builder()
                        .id(m.getId())
                        .branchNames(m.getBranches().stream()
                                .map(Branch::getBranchName)
                                .toList())
                        .merchantName(m.getMerchantName())
                        .description(m.getDescription())
                        .userOwnerId(m.getOwner().getId())
                        .overallRating(m.getOverallRating())
                        .build()).toList();
        return messageSingleton.ok(Map.of("merchantPlaces", response));
    }

    public ResponseEntity<?> getBranchesFromMerchantPlace(Long merchantPlaceId) {
        Optional<MerchantPlace> optionalMerchantPlace = merchantPlaceRepository.findById(merchantPlaceId);
        if (optionalMerchantPlace.isEmpty()) {
            messageSingleton.merchantPlaceDoesNotExist();
        }
        MerchantPlace merchantPlace = optionalMerchantPlace.get();
        if (!Objects.equals(merchantPlace.getOwner(), getUser()) && !Objects.equals(getUser().getRole(), UserRole.ADMIN)) {
            return messageSingleton.userDoesNotMatch();
        }
        List<GetBranchesFromMerchantPlaceResponse> response = merchantPlace.getBranches().stream()
                .map(b -> GetBranchesFromMerchantPlaceResponse.builder()
                        .branchId(b.getId())
                        .branchName(b.getBranchName())
                        .branchOverallScore(b.getOverallScore())
                        .address(b.getAddress())
                        .build())
                .toList();
        return messageSingleton.ok(Map.of("branches", response));
    }

    public boolean setBranchToMerchantPlace(Branch branch, MerchantPlace merchantPlace) {
        return false;
    }

    public ResponseEntity<?> getMerchantPlace(Long id) {
        Optional<MerchantPlace> optionalMerchantPlace = merchantPlaceRepository.findById(id);
        if (optionalMerchantPlace.isEmpty()) {
            messageSingleton.merchantPlaceDoesNotExist();
        }
        MerchantPlace merchantPlace = optionalMerchantPlace.get();
        GetMerchantPlaceResponse response = GetMerchantPlaceResponse.builder()
                .merchantPlaceId(merchantPlace.getId())
                .merchantPlaceName(merchantPlace.getMerchantName())
                .description(merchantPlace.getDescription())
                .ownerUsername(merchantPlace.getOwner().getUsername())
                .overallRating(merchantPlace.getOverallRating())
                .branches(merchantPlace.getBranches().stream()
                        .map(b -> GetBranchResponse.builder()
                                .branchId(b.getId())
                                .branchName(b.getBranchName())
                                .address(b.getAddress())
                                .overallScore(b.getOverallScore())
                                .build())
                        .toList())
                .build();
        return messageSingleton.ok(Map.of("merchantPlace", response));
    }
}
