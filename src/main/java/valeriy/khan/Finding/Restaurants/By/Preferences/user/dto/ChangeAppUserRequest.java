package valeriy.khan.Finding.Restaurants.By.Preferences.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.status.AppUserStatus;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.type.AppUserType;

import javax.validation.constraints.Min;
import java.sql.Timestamp;

@Validated
@Getter
@Setter
public class ChangeAppUserRequest {
    @Min(value = 5, message = "Username should be longer than 5 symbols")
    private String username;
    @Min(value = 8, message = "Password should be longer than 8 symbols")
    private String password;
    private String firstName;
    private String lastName;
    private Timestamp dateOfBirth;
    @Min(value = 8, message = "Phone should be longer than 8 symbols")
    private String phoneNumber;
    private AppUserType type;
    private AppUserStatus status;
}
