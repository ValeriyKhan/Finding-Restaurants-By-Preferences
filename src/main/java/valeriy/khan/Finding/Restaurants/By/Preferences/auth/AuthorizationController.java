package valeriy.khan.Finding.Restaurants.By.Preferences.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valeriy.khan.Finding.Restaurants.By.Preferences.auth.dto.LoginRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.auth.dto.GenerateTokenRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.CreateAppUserRequest;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("register")
    public ResponseEntity<?> registerNewAppUser(
            @RequestBody CreateAppUserRequest createAppUserRequest
    ) {
        return authorizationService.registerAppUserByUser(createAppUserRequest);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return authorizationService.login(loginRequest);
    }

    @PostMapping("access-token")
    public ResponseEntity<?> getAccessToken(
            @RequestBody GenerateTokenRequest refreshToken
    ){
        return authorizationService.generateAccessToken(refreshToken);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> getRefreshToken(
            @RequestBody GenerateTokenRequest refreshToken
    ){
        return authorizationService.generateRefreshToken(refreshToken);
    }
}
