package restaurant.app.merchantPlace.branch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateBranchRequest {
    @NotNull(message = "Cannot be empty")
    @Size(min = 1, max = 25)
    private String branchName;
    @NotNull(message = "Cannot be empty")
    private String address;
    @NotNull
    private Long merchantPlaceId;
}
