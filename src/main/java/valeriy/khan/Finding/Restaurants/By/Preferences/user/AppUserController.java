package valeriy.khan.Finding.Restaurants.By.Preferences.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.ChangeAppUserRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.CreateAdminRequest;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.dto.CreateAppUserRequest;


@RestController
@RequestMapping(path = "api/v1/user")
public class AppUserController {
    private AppUserService appUserService;

    @GetMapping("getall")
    public ResponseEntity<?> getAllAppUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return appUserService.getAllAppUsers(page, size);
    }

    @GetMapping("get/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long appUserId) {
        return appUserService.getAppUser(appUserId);
    }

    @PostMapping("create")
    public ResponseEntity<?> createAppUser(
            @RequestBody CreateAppUserRequest createAppUserRequest) {
        return appUserService.createAppUserByAdmin(createAppUserRequest);
    }

    @PutMapping("{userId}")
    public ResponseEntity<?> changeAppUser(
            @PathVariable("userId") Long userId,
            @RequestBody ChangeAppUserRequest changeAppUserRequest) {
        return appUserService.changeAppUser(userId, changeAppUserRequest);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteAppUser(
            @PathVariable("userId") Long userId) {
        return appUserService.deleteAppUser(userId);
    }

    @PostMapping("create-admin")
    public ResponseEntity<?> createAdmin(
            @RequestBody CreateAdminRequest createAdminRequest
    ) {
        return appUserService.createAdmin(createAdminRequest);
    }
}
