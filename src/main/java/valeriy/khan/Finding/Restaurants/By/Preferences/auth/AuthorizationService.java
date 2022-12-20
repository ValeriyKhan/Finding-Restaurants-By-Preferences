package valeriy.khan.Finding.Restaurants.By.Preferences.auth;

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
import valeriy.khan.Finding.Restaurants.By.Preferences.auth.dto.LoginRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.auth.dto.GenerateTokenRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.messagesingleton.MessageSingleton;
import valeriy.khan.Finding.Restaurants.By.Preferences.security.RefreshTokenEntity;
import valeriy.khan.Finding.Restaurants.By.Preferences.security.jwt.JwtUtils;
import valeriy.khan.Finding.Restaurants.By.Preferences.security.repository.RefreshTokenRepository;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.AppUser;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.AppUserRepository;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.CreateAdminRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.CreateAppUserRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.CreateAppUserResponse;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.status.AppUserStatus;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.type.AppUserType;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static valeriy.khan.Finding.Restaurants.By.Preferences.role.AppUserRole.ADMIN;
import static valeriy.khan.Finding.Restaurants.By.Preferences.user.status.AppUserStatus.ACTIVE;

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
                String accessToken = jwtUtils.generateToken(appUserDetails);
                String refreshToken = jwtUtils.generateToken(appUserDetails);
                Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(appUserDetails.getUsername());
                AppUser appUser = optionalAppUser.get();
                refreshTokenEntity.setAppUser(appUser);
                refreshTokenEntity.setToken(refreshToken);
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
        String newAccessToken = jwtUtils.generateToken(userDetails, claims);
        return messageSingleton.ok(Map.of("accessToken", newAccessToken));
    }

    public ResponseEntity<?> generateRefreshToken(GenerateTokenRequest refreshToken) {
        String token = refreshToken.getToken();
        String username = jwtUtils.extractUsername(token);
        Claims claims = jwtUtils.extractAllClaims(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtUtils.generateToken(userDetails, claims);
        String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);
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
}
