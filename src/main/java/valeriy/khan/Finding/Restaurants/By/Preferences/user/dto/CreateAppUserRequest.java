package valeriy.khan.Finding.Restaurants.By.Preferences.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import valeriy.khan.Finding.Restaurants.By.Preferences.role.AppUserRole;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.type.AppUserType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Validated
@Getter
@Setter
public class CreateAppUserRequest {
    @Size(min = 5, message = "Username should be longer than 5 symbols")
    @NotNull(message = "Username should not be empty")
    private String username;
    @Size(min = 10, message = "Username should be longer than 5 symbols")
    @NotNull(message = "Password should not be empty")
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
    @NotNull(message = "Field should not be empty")
    private AppUserType type;
    @NotNull(message = "Field should not be empty")
    private AppUserRole role;
}
