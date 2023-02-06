package restaurant.app.merchantPlace.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.MerchantPlace;
import restaurant.app.merchantPlace.MerchantPlaceRepository;
import restaurant.app.merchantPlace.branch.dto.*;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.preference.Preference;
import restaurant.app.preference.PreferenceService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final MessageSingleton messageSingleton;
    private final PreferenceService preferenceService;
    private final MerchantPlaceRepository merchantPlaceRepository;

    public ResponseEntity<?> createBranch(CreateBranchRequest createBranchRequest) {
        Optional<Branch> optionalBranch = branchRepository
                .findByBranchName(createBranchRequest.getBranchName());
        if (optionalBranch.isPresent()) {
            return messageSingleton.branchAlreadyExists();
        }
        Optional<MerchantPlace> optionalMerchantPlace = merchantPlaceRepository.findById(createBranchRequest.getMerchantPlaceId());
        if (optionalMerchantPlace.isEmpty()) {
            return messageSingleton.merchantPlaceDoesNotExist();
        }
        MerchantPlace merchantPlace = optionalMerchantPlace.get();
        String branchName = createBranchRequest.getBranchName();
        String address = createBranchRequest.getAddress();
        Branch branch = Branch.builder()
                .branchName(branchName)
                .merchantPlace(merchantPlace)
                .address(address)
                .build();
        branchRepository.save(branch);
        CreateBranchResponse response = CreateBranchResponse.builder()
                .id(branch.getId())
                .branchName(branchName)
                .merchantPlaceNameOwner(merchantPlace.getMerchantName())
                .address(address)
                .build();
        return messageSingleton.ok(Map.of("branch", response));
    }

    public ResponseEntity<?> getBranch(Long id) {
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if (optionalBranch.isEmpty()) {
            return messageSingleton.branchNotFound();
        }
        Branch branch = optionalBranch.get();
        CreateBranchResponse response = CreateBranchResponse.builder()
                .id(branch.getId())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .preferenceEntities(branch.getPreferenceEntities())
                .build();
        return messageSingleton.ok(Map.of("branch", response));
    }

    public ResponseEntity<?> getAllBranches(int page, int size) {
        Page<Branch> branches = branchRepository.findAll(PageRequest.of(page, size));
        List<GetAllBranchesResponse> response = branches.stream().map(b -> GetAllBranchesResponse.builder()
                .branchId(b.getId())
                .branchName(b.getBranchName())
                .overallRating(b.getOverallScore())
                .merchantPlaceName(b.getMerchantPlace().getMerchantName())
                .merchantPlaceId(b.getMerchantPlace().getId())
                .address(b.getAddress())
                .build()).toList();
        return messageSingleton.ok(Map.of("branches", response));
    }

    public ResponseEntity<?> changeBranch(ChangeBranch changeBranch) {
        Optional<Branch> optionalBranch = branchRepository.findById(changeBranch.getId());
        if (optionalBranch.isEmpty()) {
            return messageSingleton.branchNotFound();
        }
        Branch branch = optionalBranch.get();
        branch.setBranchName(changeBranch.getBranchName());
        branch.setPreferenceEntities(changeBranch.getPreferenceEntities());
        branch.setAddress(changeBranch.getAddress());
        branchRepository.save(branch);
        return messageSingleton.ok(Map.of("message: User changed", branch));
    }

    public ResponseEntity<?> setPreferencesToBranch(SetPreferencesToBranchRequest setPreferencesToBranchRequest) {
        Optional<Branch> optionalBranch = branchRepository
                .findById(setPreferencesToBranchRequest.getBranchId());
        if (optionalBranch.isEmpty()) {
            return messageSingleton.branchNotFound();
        }
        List<Preference> preferencesToSetToEntity = preferenceService.matchPreferences(setPreferencesToBranchRequest.getPreferenceIdList());
        Branch branch = optionalBranch.get();
        branch.setPreferenceEntities(preferencesToSetToEntity);
        branchRepository.save(branch);
        return messageSingleton.ok(Map.of("preferences", SetPreferencesToBranchResponse.builder()
                .branchId(branch.getId())
                .branchName(branch.getBranchName())
                .preferenceListOfBranches(preferencesToSetToEntity)
                .build()));
    }
}
