package restaurant.app.preference;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.app.preference.dto.CreatePreferenceRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/preference")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PostMapping
    public ResponseEntity<?> createPreference(
            @Valid @RequestBody CreatePreferenceRequest createPreferenceRequest
    ){
        return preferenceService.createPreference(createPreferenceRequest);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllPreferences(){
        return preferenceService.getAllPreferences();
    }

    @DeleteMapping
    public ResponseEntity<?> deletePreference(
            @RequestParam("id") Long id
    ){
        return preferenceService.deletePreference(id);
    }
}
