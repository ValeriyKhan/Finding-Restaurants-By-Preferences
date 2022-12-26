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
import restaurant.app.threadLocalSingleton.ThreadLocalSingleton;
import restaurant.app.user.User;
import restaurant.app.user.UserRepository;
import restaurant.app.user.dto.CreateAdminRequest;
import restaurant.app.auth.dto.LoginRequest;
import restaurant.app.user.dto.CreateUserRequest;
import restaurant.app.user.dto.CreateUserResponse;
import restaurant.app.user.status.UserStatus;
import restaurant.app.user.type.UserType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static restaurant.app.role.UserRole.ADMIN;
import static restaurant.app.user.status.UserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MessageSingleton messageSingleton;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;


    public ResponseEntity<?> registerAppUserByUser(CreateUserRequest createUserRequest) {
        Optional<User> appUserOptional = userRepository.findByUsernameAndPhoneNumber(
                createUserRequest.getUsername(), createUserRequest.getPhoneNumber());
        if (appUserOptional.isPresent()) {
            return messageSingleton.userIsExist();
        }
        if (Objects.equals(createUserRequest.getRole(), ADMIN)) {
            return messageSingleton.failedCreatingAUser();
        }

        User user = User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .dateOfBirth(createUserRequest.getDateOfBirth())
                .phoneNumber(createUserRequest.getPhoneNumber())
                .type(createUserRequest.getType())
                .status(UserStatus.ACTIVE)
                .role(createUserRequest.getRole())
                .build();
        user = userRepository.save(user);
        CreateUserResponse response = CreateUserResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
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
                Optional<User> optionalAppUser = userRepository.findByUsername(appUserDetails.getUsername());
                User user = optionalAppUser.get();
                user.setSalt(salt);
                refreshTokenEntity.setUser(user);
                refreshTokenEntity.setToken(refreshToken);
                userRepository.save(user);
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
        Optional<User> appUserOptional = userRepository.findByUsername(username);
        if (appUserOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User user = appUserOptional.get();
        user.setSalt(salt);
        userRepository.save(user);
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
        Optional<User> appUserOptional = userRepository.findByUsername(username);
        if (appUserOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User user = appUserOptional.get();
        user.setSalt(salt);
        userRepository.save(user);

        return messageSingleton.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken));
    }

    public ResponseEntity<?> createAdmin(
            CreateAdminRequest createAdminRequest) {
        Optional<User> appUserOptional = userRepository.findByUsernameAndPhoneNumber(
                createAdminRequest.getUsername(), createAdminRequest.getPhoneNumber());
        if (appUserOptional.isPresent()) {
            return messageSingleton.userIsExist();
        }
        User user = User.builder()
                .username(createAdminRequest.getUsername())
                .password(passwordEncoder.encode(createAdminRequest.getPassword()))
                .firstName(createAdminRequest.getFirstName())
                .lastName(createAdminRequest.getLastName())
                .dateOfBirth(createAdminRequest.getDateOfBirth())
                .phoneNumber(createAdminRequest.getPhoneNumber())
                .type(UserType.STAFF)
                .status(ACTIVE)
                .role(ADMIN)
                .build();
        CreateUserResponse response = CreateUserResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .build();
        userRepository.save(user);
        return messageSingleton.ok(Map.of("admin", response));
    }

    public String generateSalt() {
        return UUID.randomUUID().toString();
    }
}
