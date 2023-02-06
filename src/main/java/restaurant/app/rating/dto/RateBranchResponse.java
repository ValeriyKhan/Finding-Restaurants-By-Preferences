package restaurant.app.rating.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateBranchResponse {
    public Long userId;
    public Long branchId;
    public int score;
}
