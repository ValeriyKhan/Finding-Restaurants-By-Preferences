package valeriy.khan.Finding.Restaurants.By.Preferences.messagesingleton;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Component
public class MessageSingleton {
    public ResponseEntity<?> ok(Object body) {
        return new ResponseEntity<>(body, OK);
    }

    public ResponseEntity<?> userDoesNotExist() {
        return new ResponseEntity<>(Map.of("message", "User not found"), NOT_FOUND);
    }

    public ResponseEntity<?> userIsExist() {
        return new ResponseEntity<>(Map.of("error", "User already exists"), FORBIDDEN);
    }

    public ResponseEntity<?> userIsBlocked() {
        return new ResponseEntity<>(Map.of("error", "User is blocked"), FORBIDDEN);
    }

    public ResponseEntity<?> tokenIsNotValid() {
        return new ResponseEntity<>(Map.of("error", "Token is not valid"), FORBIDDEN);
    }

    public ResponseEntity<?> tokenIsNotPresent() {
        return new ResponseEntity<>(Map.of("error", "Token is not present"), BAD_REQUEST);
    }

    public ResponseEntity<?> passwordDoesNotMatch() {
        return new ResponseEntity<>(Map.of("error", "Wrong password!"), BAD_REQUEST);
    }

    public ResponseEntity<?> failedLogin() {
        return new ResponseEntity<>(FORBIDDEN);
    }

    public ResponseEntity<?> failedLogin(String errorMessage) {
        return new ResponseEntity<>(Map.of("error", errorMessage), FORBIDDEN);
    }

    public ResponseEntity<?> failedCreatingAUser() {
        return new ResponseEntity<>(Map.of("error", "Trying create an admin. Permission denied"), FORBIDDEN);
    }
}
