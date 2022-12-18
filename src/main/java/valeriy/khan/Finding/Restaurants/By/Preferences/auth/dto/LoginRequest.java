package valeriy.khan.Finding.Restaurants.By.Preferences.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginRequest {
    private final String username;
    private final String password;
}
