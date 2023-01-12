package restaurant.app.merchantPlace.branch.dto;

import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.Preference;

import java.util.List;

@Getter
@Setter
public class ChangeBranch {
    private Long id;
    private String branchName;
    private List<Preference> preferenceEntities;
    private String address;
}
