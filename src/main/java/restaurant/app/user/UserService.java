package restaurant.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.user.dto.ChangeUserRequest;
import restaurant.app.user.dto.CreateUserRequest;
import restaurant.app.user.dto.GetUserResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static restaurant.app.role.UserRole.*;
import static restaurant.app.user.status.UserStatus.*;

@Service
@RequiredArgsConstructor
@Validated
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final MessageSingleton messageSingleton;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> getAllUsers(int page, int size) {
        Page<User> appUserList = appUserRepository.findAll(PageRequest.of(page, size));

        return messageSingleton.ok(Map.of("users", appUserList));
    }

    public ResponseEntity<?> getUser(Long appUserId) {
        Optional<User> appUserOptional = appUserRepository.findById(appUserId);
        if (appUserOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User user = appUserOptional.get();
        GetUserResponse getUserResponse = GetUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .type(user.getType())
                .build();
        return messageSingleton.ok(Map.of("user", getUserResponse));
    }

    public ResponseEntity<?> createUserByAdmin(CreateUserRequest createUserRequest) {
        Optional<User> appUserOptional = appUserRepository.findByUsernameAndPhoneNumber(
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
                .status(ACTIVE)
                .role(createUserRequest.getRole())
                .build();
        appUserRepository.save(user);
        return messageSingleton.ok(Map.of("user", user));
    }

    public ResponseEntity<?> changeUser(@NotNull Long userId,
                                        @Valid ChangeUserRequest changeUserRequest) {
        Optional<User> optionalAppUser = appUserRepository.findById(userId);
        if (optionalAppUser.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User newUser = optionalAppUser.get();
        newUser.setUsername(changeUserRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(changeUserRequest.getPassword()));
        newUser.setFirstName(changeUserRequest.getFirstName());
        newUser.setLastName(changeUserRequest.getLastName());
        newUser.setDateOfBirth(changeUserRequest.getDateOfBirth());
        newUser.setPhoneNumber(changeUserRequest.getPhoneNumber());
        newUser.setType(changeUserRequest.getType());
        newUser.setStatus(changeUserRequest.getStatus());
        appUserRepository.save(newUser);
        return messageSingleton.ok(Map.of("user", newUser));
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        Optional<User> optionalAppUser = appUserRepository.findById(userId);
        if (optionalAppUser.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User user = optionalAppUser.get();
        user.setStatus(DELETED);
        appUserRepository.save(user);
        return messageSingleton.ok(Map.of("message", "User with ID " + user.getId() + " deleted"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> appUserOptional = appUserRepository.findByUsername(username);
        if (appUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return appUserOptional.get();
    }
}
