package restaurant.app.merchantPlace.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.app.merchantPlace.branch.dto.ChangeBranch;
import restaurant.app.merchantPlace.branch.dto.CreateBranchRequest;
import restaurant.app.merchantPlace.branch.dto.SetPreferencesToBranchRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/branch")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<?> createBranch(
            @Valid @RequestBody CreateBranchRequest createFilialRequest
    ) {
        return branchService.createBranch(createFilialRequest);
    }

    @GetMapping
    public ResponseEntity<?> getBranch(
            @RequestParam("id") Long id
    ) {
        return branchService.getBranch(id);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllBranches(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return branchService.getAllBranches(page, size);
    }

    @PutMapping
    public ResponseEntity<?> changeBranch(
            @RequestBody ChangeBranch changeBranch
    ) {
        return branchService.changeBranch(changeBranch);
    }
    @PostMapping("set-preferences")
    public ResponseEntity<?> setPreferencesToBranch(
            @RequestBody SetPreferencesToBranchRequest setPreferencesToBranchRequest
    ) {
        return branchService.setPreferencesToBranch(setPreferencesToBranchRequest);
    }


}
