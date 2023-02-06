package restaurant.app.merchantPlace.dto;

import lombok.Builder;

import java.util.List;
@Builder
public class GetAllMerchantPlaceResponse {
    public Long id;
    public List<String> branchNames;
    public String merchantName;
    public String description;
    public Long userOwnerId;
    public double overallRating;
}
