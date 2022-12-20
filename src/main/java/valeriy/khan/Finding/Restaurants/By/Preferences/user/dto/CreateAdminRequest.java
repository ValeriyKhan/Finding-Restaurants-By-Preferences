package valeriy.khan.Finding.Restaurants.By.Preferences.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class CreateAdminRequest {
    @Size(min = 5, message = "Username should be longer than 5 symbols")
    private String username;
    @Size(min = 10, message = "Password should be longer than 10 symbols")
    private String password;
    @NotNull(message = "Field should not be empty")
    private String firstName;
    @NotNull(message = "Field should not be empty")
    private String lastName;
    @NotNull(message = "Field should not be empty")
    private Timestamp dateOfBirth;
    @NotNull(message = "Field should not be empty")
    private String phoneNumber;
}
