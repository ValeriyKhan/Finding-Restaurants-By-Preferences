package restaurant.app.merchantPlace.dto;

import lombok.Builder;

@Builder
public class GetBranchResponse {
    public Long branchId;
    public String branchName;
    public double overallScore;
    public String address;

}
