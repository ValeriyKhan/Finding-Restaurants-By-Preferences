package restaurant.app.merchantPlace.branch.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetPreferencesToBranchRequest {
    private Long branchId;
    private List<Long> preferenceIdList;
}
