package restaurant.app.merchantPlace.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public class GetBranchesFromMerchantPlaceResponse {
    public Long branchId;
    public String branchName;
    public double branchOverallScore;
    public String address;
}
