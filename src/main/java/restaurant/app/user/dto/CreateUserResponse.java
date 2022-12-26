package restaurant.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;

@AllArgsConstructor
@Builder
public class CreateUserResponse {
    public String username;
    public String firstName;
    public String lastName;
    public Timestamp dateOfBirth;
    public String phoneNumber;
}
