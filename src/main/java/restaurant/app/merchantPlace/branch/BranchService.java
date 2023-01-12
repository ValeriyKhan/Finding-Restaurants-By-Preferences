package restaurant.app.merchantPlace.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.branch.dto.*;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.preference.Preference;
import restaurant.app.preference.PreferenceRepository;
import restaurant.app.preference.PreferenceService;
import restaurant.app.threadLocalSingleton.ThreadLocalSingleton;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final PreferenceRepository preferenceRepository;
    private final MessageSingleton messageSingleton;
    private final PreferenceService preferenceService;

    public ResponseEntity<?> createBranch(CreateBranchRequest createBranchRequest) {
        Optional<Branch> optionalBranch = branchRepository
                .findByBranchName(createBranchRequest.getBranchName());
        if (optionalBranch.isPresent()) {
            return messageSingleton.branchAlreadyExists();
        }
        Branch branch = Branch.builder()
                .branchName(createBranchRequest.getBranchName())
                .branchOwner(ThreadLocalSingleton.getUser())
                .address(createBranchRequest.getAddress())
                .build();
        branchRepository.save(branch);
        CreateBranchResponse response = CreateBranchResponse.builder()
                .id(branch.getId())
                .branchName(createBranchRequest.getBranchName())
                .address(createBranchRequest.getAddress())
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
        return messageSingleton.ok(Map.of("branches", branches));
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
