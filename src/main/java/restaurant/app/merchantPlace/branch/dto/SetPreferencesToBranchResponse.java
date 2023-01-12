package restaurant.app.merchantPlace.branch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.Preference;

import java.util.List;

@Getter
@Setter
@Builder
public class SetPreferencesToBranchResponse {
    private Long branchId;
    private String branchName;
    private List<Preference> preferenceListOfBranches;
}
