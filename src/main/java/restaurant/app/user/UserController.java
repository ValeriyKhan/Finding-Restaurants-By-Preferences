package restaurant.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.app.user.dto.AddPreferencesToUserRequest;
import restaurant.app.user.dto.ChangeUserRequest;
import restaurant.app.user.dto.CreateUserRequest;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("get-all/")
    public ResponseEntity<?> getAllUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("get/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping("create")
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUserByAdmin(createUserRequest);
    }

    @PutMapping("{userId}")
    public ResponseEntity<?> changeAppUser(
            @PathVariable("userId") Long userId,
            @RequestBody ChangeUserRequest changeUserRequest) {
        return userService.changeUser(userId, changeUserRequest);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("userId") Long userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping("add-preferences")
    public ResponseEntity<?> addPreferencesToUser(
            @Valid @RequestBody AddPreferencesToUserRequest addPreferencesToUserRequest
    ) {
        return userService.addPreferencesToUser(addPreferencesToUserRequest);
    }
}
