package restaurant.app.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.Preference;

import java.util.List;

@Getter
@Setter
@Builder
public class AddPreferencesToUserResponse {
    private Long userId;
    private String username;
    private List<Preference> userPreferenceListEntity;
}
