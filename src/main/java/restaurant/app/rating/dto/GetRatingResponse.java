package restaurant.app.rating.dto;

import lombok.Builder;

@Builder
public class GetRatingResponse {
    public Long ratingId;
    public Long userId;
    public Long branchId;
    public int score;
}
