package restaurant.app.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.app.auth.dto.GenerateTokenRequest;
import restaurant.app.user.dto.CreateAdminRequest;
import restaurant.app.auth.dto.LoginRequest;
import restaurant.app.user.dto.CreateUserRequest;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("register")
    public ResponseEntity<?> registerNewAppUser(
          @Valid @RequestBody CreateUserRequest createUserRequest
    ) {
        return authorizationService.registerAppUserByUser(createUserRequest);
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
    @PostMapping("create-admin")
    public ResponseEntity<?> createAdmin(
            @Valid @RequestBody CreateAdminRequest createAdminRequest
    ) {
        return authorizationService.createAdmin(createAdminRequest);
    }
}
