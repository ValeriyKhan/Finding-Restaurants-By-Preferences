package restaurant.app.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import restaurant.app.user.type.UserType;
import restaurant.app.user.status.UserStatus;

import javax.validation.constraints.Min;
import java.sql.Timestamp;

@Validated
@Getter
@Setter
public class ChangeUserRequest {
    @Min(value = 5, message = "Username should be longer than 5 symbols")
    private String username;
    @Min(value = 8, message = "Password should be longer than 8 symbols")
    private String password;
    private String firstName;
    private String lastName;
    private Timestamp dateOfBirth;
    @Min(value = 8, message = "Phone should be longer than 8 symbols")
    private String phoneNumber;
    private UserType type;
    private UserStatus status;
}
