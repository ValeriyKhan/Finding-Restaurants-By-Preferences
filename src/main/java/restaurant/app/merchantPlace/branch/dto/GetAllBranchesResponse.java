package restaurant.app.merchantPlace.branch.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllBranchesResponse {
    public Long branchId;
    public String branchName;
    public double overallRating;
    public String merchantPlaceName;
    public Long merchantPlaceId;
    private String address;
}
