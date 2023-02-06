package restaurant.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import restaurant.app.preference.Preference;
import restaurant.app.role.UserRole;
import restaurant.app.user.type.UserType;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Builder
public class GetUserResponse {
    public Long id;
    public String username;
    public String firstName;
    public String lastName;
    public Timestamp dateOfBirth;
    public String phoneNumber;
    public UserRole role;
    public UserType type;
    public List<Preference> preferenceList;
}
