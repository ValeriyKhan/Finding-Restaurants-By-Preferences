package restaurant.app.merchantPlace.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class GetMerchantPlaceResponse {
    public Long merchantPlaceId;
    public String merchantPlaceName;
    public String description;
    public String ownerUsername;
    public double overallRating;
    public List<GetBranchResponse> branches;
}
