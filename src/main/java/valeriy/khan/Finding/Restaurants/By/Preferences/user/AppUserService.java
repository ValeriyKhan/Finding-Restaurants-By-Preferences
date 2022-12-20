package valeriy.khan.Finding.Restaurants.By.Preferences.user;

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
import valeriy.khan.Finding.Restaurants.By.Preferences.messagesingleton.MessageSingleton;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.ChangeAppUserRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.CreateAppUserRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.GetUserResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static valeriy.khan.Finding.Restaurants.By.Preferences.role.AppUserRole.*;
import static valeriy.khan.Finding.Restaurants.By.Preferences.user.status.AppUserStatus.*;

@Service
@RequiredArgsConstructor
@Validated
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final MessageSingleton messageSingleton;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> getAllAppUsers(int page, int size) {
        Page<AppUser> appUserList = appUserRepository.findAll(PageRequest.of(page, size));

        return messageSingleton.ok(Map.of("users", appUserList));
    }

    public ResponseEntity<?> getAppUser(Long appUserId) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(appUserId);
        if (appUserOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        AppUser appUser = appUserOptional.get();
        GetUserResponse getUserResponse = GetUserResponse.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .dateOfBirth(appUser.getDateOfBirth())
                .phoneNumber(appUser.getPhoneNumber())
                .role(appUser.getRole())
                .type(appUser.getType())
                .build();
        return messageSingleton.ok(Map.of("user", getUserResponse));
    }

    public ResponseEntity<?> createAppUserByAdmin(CreateAppUserRequest createAppUserRequest) {
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
                .status(ACTIVE)
                .role(createAppUserRequest.getRole())
                .build();
        appUserRepository.save(appUser);
        return messageSingleton.ok(Map.of("user", appUser));
    }

    public ResponseEntity<?> changeAppUser(@NotNull Long userId,
                                           @Valid ChangeAppUserRequest changeAppUserRequest) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(userId);
        if (optionalAppUser.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        AppUser newAppUser = optionalAppUser.get();
        newAppUser.setUsername(changeAppUserRequest.getUsername());
        newAppUser.setPassword(passwordEncoder.encode(changeAppUserRequest.getPassword()));
        newAppUser.setFirstName(changeAppUserRequest.getFirstName());
        newAppUser.setLastName(changeAppUserRequest.getLastName());
        newAppUser.setDateOfBirth(changeAppUserRequest.getDateOfBirth());
        newAppUser.setPhoneNumber(changeAppUserRequest.getPhoneNumber());
        newAppUser.setType(changeAppUserRequest.getType());
        newAppUser.setStatus(changeAppUserRequest.getStatus());
        appUserRepository.save(newAppUser);
        return messageSingleton.ok(Map.of("user", newAppUser));
    }

    public ResponseEntity<?> deleteAppUser(Long userId) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(userId);
        if (optionalAppUser.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        AppUser appUser = optionalAppUser.get();
        appUser.setStatus(DELETED);
        appUserRepository.save(appUser);
        return messageSingleton.ok(Map.of("message", "User with ID " + appUser.getId() + " deleted"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
        if (appUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return appUserOptional.get();
    }
}
