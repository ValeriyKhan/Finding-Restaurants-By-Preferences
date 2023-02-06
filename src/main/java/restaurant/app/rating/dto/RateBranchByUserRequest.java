package restaurant.app.rating.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RateBranchByUserRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long branchId;
    @Min(value = 1)
    @Max(value = 5)
    @NotNull
    private int rating;
}
