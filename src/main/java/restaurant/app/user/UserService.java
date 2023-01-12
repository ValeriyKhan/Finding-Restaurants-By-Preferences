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
import restaurant.app.preference.Preference;
import restaurant.app.preference.PreferenceService;
import restaurant.app.user.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static restaurant.app.role.UserRole.*;
import static restaurant.app.user.status.UserStatus.*;

@Service
@RequiredArgsConstructor
@Validated
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageSingleton messageSingleton;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PreferenceService preferenceService;

    public ResponseEntity<?> getAllUsers(int page, int size) {
        Page<User> userList = userRepository.findAll(PageRequest.of(page, size));

        return messageSingleton.ok(Map.of("users", userList));
    }

    public ResponseEntity<?> getUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User user = userOptional.get();
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
        Optional<User> userOptional = userRepository.findByUsernameAndPhoneNumber(
                createUserRequest.getUsername(), createUserRequest.getPhoneNumber());
        if (userOptional.isPresent()) {
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
        userRepository.save(user);
        return messageSingleton.ok(Map.of("user", user));
    }

    public ResponseEntity<?> changeUser(@NotNull Long userId,
                                        @Valid ChangeUserRequest changeUserRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User newUser = optionalUser.get();
        newUser.setUsername(changeUserRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(changeUserRequest.getPassword()));
        newUser.setFirstName(changeUserRequest.getFirstName());
        newUser.setLastName(changeUserRequest.getLastName());
        newUser.setDateOfBirth(changeUserRequest.getDateOfBirth());
        newUser.setPhoneNumber(changeUserRequest.getPhoneNumber());
        newUser.setType(changeUserRequest.getType());
        newUser.setStatus(changeUserRequest.getStatus());
        userRepository.save(newUser);
        return messageSingleton.ok(Map.of("user", newUser));
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User user = optionalUser.get();
        user.setStatus(DELETED);
        userRepository.save(user);
        return messageSingleton.ok(Map.of("message", "User with ID " + user.getId() + " deleted"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return userOptional.get();
    }

    public ResponseEntity<?> addPreferencesToUser(AddPreferencesToUserRequest addPreferencesToUserRequest) {
        Optional<User> userOptional = userRepository.findById(addPreferencesToUserRequest.getUserId());
        if (userOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        List<Preference> preferenceListToSet = preferenceService.matchPreferences(addPreferencesToUserRequest.getPreferenceIds());
        if (preferenceListToSet.isEmpty()) {
            return messageSingleton.badRequest();
        }
        User user = userOptional.get();
        user.setPreferenceList(preferenceListToSet);
        userRepository.save(user);
        return messageSingleton.ok(Map.of("user", AddPreferencesToUserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .userPreferenceListEntity(preferenceListToSet)
                .build())
        );
    }
}
