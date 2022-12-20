package valeriy.khan.Finding.Restaurants.By.Preferences.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public class LoginRequest {
    @NotNull(message = "Username should not be empty")
    private final String username;
    @NotNull(message = "Password should not be empty")
    private final String password;
}
