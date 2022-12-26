package restaurant.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.app.user.dto.ChangeAppUserRequest;
import restaurant.app.user.dto.CreateAppUserRequest;


@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("get-all/")
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
}
