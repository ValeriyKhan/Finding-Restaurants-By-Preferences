package restaurant.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import restaurant.app.role.AppUserRole;
import restaurant.app.user.type.AppUserType;

import java.sql.Timestamp;

@AllArgsConstructor
@Builder
public class GetUserResponse {
    public Long id;
    public String username;
    public String firstName;
    public String lastName;
    public Timestamp dateOfBirth;
    public String phoneNumber;
    public AppUserRole role;
    public AppUserType type;
}
