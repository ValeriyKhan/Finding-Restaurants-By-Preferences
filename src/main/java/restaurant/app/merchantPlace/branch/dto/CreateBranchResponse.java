package restaurant.app.merchantPlace.branch.dto;

import lombok.Builder;
import restaurant.app.preference.Preference;

import java.util.List;
@Builder
public class CreateBranchResponse {
    public Long id;
    public String branchName;
    public String address;
    public List<Preference> preferenceEntities;
}
