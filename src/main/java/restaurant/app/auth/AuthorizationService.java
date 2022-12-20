package restaurant.app.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import restaurant.app.auth.dto.GenerateTokenRequest;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.security.RefreshTokenEntity;
import restaurant.app.security.jwt.JwtUtils;
import restaurant.app.security.repository.RefreshTokenRepository;
import restaurant.app.user.AppUser;
import restaurant.app.user.AppUserRepository;
import restaurant.app.user.dto.CreateAdminRequest;
import restaurant.app.auth.dto.LoginRequest;
import restaurant.app.user.dto.CreateAppUserRequest;
import restaurant.app.user.dto.CreateAppUserResponse;
import restaurant.app.user.status.AppUserStatus;
import restaurant.app.user.type.AppUserType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static restaurant.app.role.AppUserRole.ADMIN;
import static restaurant.app.user.status.AppUserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final AppUserRepository appUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MessageSingleton messageSingleton;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;


    public ResponseEntity<?> registerAppUserByUser(CreateAppUserRequest createAppUserRequest) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsernameAndPhoneNumber(
                createAppUserRequest.getUsername(), createAppUserRequest.getPhoneNumber());
        if (appUserOptional.isPresent()) {
            return messageSingleton.userIsExist();
        }
        if (Objects.equals(createAppUserRequest.getRole(), ADMIN)) {
            return messageSingleton.failedCreatingAUser();
        }

        AppUser appUser = AppUser.builder()
                .username(createAppUserRequest.getUsername())
                .password(passwordEncoder.encode(createAppUserRequest.getPassword()))
                .firstName(createAppUserRequest.getFirstName())
                .lastName(createAppUserRequest.getLastName())
                .dateOfBirth(createAppUserRequest.getDateOfBirth())
                .phoneNumber(createAppUserRequest.getPhoneNumber())
                .type(createAppUserRequest.getType())
                .status(AppUserStatus.ACTIVE)
                .role(createAppUserRequest.getRole())
                .build();
        appUser = appUserRepository.save(appUser);
        CreateAppUserResponse response = CreateAppUserResponse.builder()
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .dateOfBirth(appUser.getDateOfBirth())
                .phoneNumber(appUser.getPhoneNumber())
                .build();
        return messageSingleton.ok(Map.of("user", response));
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                UserDetails appUserDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
                if (!appUserDetails.isEnabled()) {
                    return messageSingleton.userIsBlocked();
                }
                RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
                String salt = generateSalt();
                String accessToken = jwtUtils.generateToken(appUserDetails, salt);
                String refreshToken = jwtUtils.generateToken(appUserDetails, salt);
                Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(appUserDetails.getUsername());
                AppUser appUser = optionalAppUser.get();
                appUser.setSalt(salt);
                refreshTokenEntity.setAppUser(appUser);
                refreshTokenEntity.setToken(refreshToken);
                appUserRepository.save(appUser);
                refreshTokenRepository.save(refreshTokenEntity);
                return messageSingleton.ok(Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken
                ));
            }
        } catch (BadCredentialsException e) {
            return messageSingleton.passwordDoesNotMatch();
        } catch (AuthenticationException e) {
            return messageSingleton.failedLogin();
        }
        return messageSingleton.failedLogin("Failed login");
    }

    public ResponseEntity<?> generateAccessToken(GenerateTokenRequest refreshToken) {
        String token = refreshToken.getToken();
        String username = jwtUtils.extractUsername(token);
        Claims claims = jwtUtils.extractAllClaims(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String salt = generateSalt();
        String newAccessToken = jwtUtils.generateToken(userDetails, claims, salt);
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
        if (appUserOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        AppUser appUser = appUserOptional.get();
        appUser.setSalt(salt);
        appUserRepository.save(appUser);
        return messageSingleton.ok(Map.of("accessToken", newAccessToken));
    }

    public ResponseEntity<?> generateRefreshToken(GenerateTokenRequest refreshToken) {
        String token = refreshToken.getToken();
        String username = jwtUtils.extractUsername(token);
        Claims claims = jwtUtils.extractAllClaims(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String salt = generateSalt();
        String newAccessToken = jwtUtils.generateToken(userDetails, claims, salt);
        String newRefreshToken = jwtUtils.generateRefreshToken(userDetails, salt);
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
        if (appUserOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        AppUser appUser = appUserOptional.get();
        appUser.setSalt(salt);
        appUserRepository.save(appUser);

        return messageSingleton.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken));
    }

    public ResponseEntity<?> createAdmin(
            CreateAdminRequest createAdminRequest) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsernameAndPhoneNumber(
                createAdminRequest.getUsername(), createAdminRequest.getPhoneNumber());
        if (appUserOptional.isPresent()) {
            return messageSingleton.userIsExist();
        }
        AppUser appUser = AppUser.builder()
                .username(createAdminRequest.getUsername())
                .password(passwordEncoder.encode(createAdminRequest.getPassword()))
                .firstName(createAdminRequest.getFirstName())
                .lastName(createAdminRequest.getLastName())
                .dateOfBirth(createAdminRequest.getDateOfBirth())
                .phoneNumber(createAdminRequest.getPhoneNumber())
                .type(AppUserType.STAFF)
                .status(ACTIVE)
                .role(ADMIN)
                .build();
        CreateAppUserResponse response = CreateAppUserResponse.builder()
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .dateOfBirth(appUser.getDateOfBirth())
                .phoneNumber(appUser.getPhoneNumber())
                .build();
        appUserRepository.save(appUser);
        return messageSingleton.ok(Map.of("admin", response));
    }

    public String generateSalt() {
        return UUID.randomUUID().toString();
    }
}
