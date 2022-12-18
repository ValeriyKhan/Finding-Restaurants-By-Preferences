package valeriy.khan.Finding.Restaurants.By.Preferences.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.type.AppUserType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class CreateAdminRequest {
    @Min(value = 5, message = "Username should be longer than 5 symbols")
    @NotNull(message = "Username should not be empty")
    private String username;
    @Min(value = 10, message = "Password should be longer than 10 symbols")
    @NotNull
    private String password;
    @NotNull(message = "Field should not be empty")
    private String firstName;
    @NotNull(message = "Field should not be empty")
    private String lastName;
    @NotNull(message = "Field should not be empty")
    private Timestamp dateOfBirth;
    @NotNull(message = "Field should not be empty")
    @Min(value = 8, message = "Phone should be longer than 8 symbols")
    private String phoneNumber;
}
