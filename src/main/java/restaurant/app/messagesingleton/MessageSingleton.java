package restaurant.app.messagesingleton;

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

    public ResponseEntity<?> branchNotFound() {
        return new ResponseEntity<>(Map.of("error", "Branch not found"), BAD_REQUEST);
    }

    public ResponseEntity<?> branchAlreadyExists() {
        return new ResponseEntity<>(Map.of("error", "Branch already exists"), BAD_REQUEST);
    }

    public ResponseEntity<?> preferenceAlreadyExists() {
        return new ResponseEntity<>(Map.of("error", " Preference already exists"), BAD_REQUEST);
    }

    public ResponseEntity<?> preferenceNotFound() {
        return new ResponseEntity<>(Map.of("error", " Some preferences not found in list"), BAD_REQUEST);
    }

    public ResponseEntity<?> badRequest() {
        return new ResponseEntity<>(Map.of("error", "Bad request!"), BAD_REQUEST);
    }

    public ResponseEntity<?> messageAlreadyExists() {
        return new ResponseEntity<>(Map.of("error", "This message already exists!"), BAD_REQUEST);
    }

    public ResponseEntity<?> messageDoesNotExist() {
        return new ResponseEntity<>(Map.of("error", "This message does not exist!"), BAD_REQUEST);
    }

    public ResponseEntity<?> ratingDoesNotExist() {
        return new ResponseEntity<>(Map.of("error", "Rating does not exist!"), BAD_REQUEST);
    }

    public ResponseEntity<?> merchantPlaceDoesNotExist() {
        return new ResponseEntity<>(Map.of("error", "MerchantPlace does not exist!"), BAD_REQUEST);
    }

    public ResponseEntity<?> merchantAlreadyExist() {
        return new ResponseEntity<>(Map.of("error", "MerchantPlace already exist!"), BAD_REQUEST);
    }

    public ResponseEntity<?> userDoesNotMatch() {
        return new ResponseEntity<>(Map.of("error", "User does not match"), BAD_REQUEST);
    }
}
