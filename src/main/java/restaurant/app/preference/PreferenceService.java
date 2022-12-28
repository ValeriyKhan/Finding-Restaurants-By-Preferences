package restaurant.app.preference;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.preference.dto.CreatePreferenceRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;
    private final MessageSingleton messageSingleton;

    public ResponseEntity<?> createPreference(CreatePreferenceRequest createPreferenceRequest) {
        Optional<Preference> optionalPreference = preferenceRepository.findPreferenceByName(createPreferenceRequest.getName());
        if (optionalPreference.isPresent()) {
            return messageSingleton.preferenceAlreadyExists();
        }
        Preference preference = new Preference();
        preference.setName(createPreferenceRequest.getName());
        preferenceRepository.save(preference);
        return messageSingleton.ok(Map.of("preference", preference));
    }

    public ResponseEntity<?> getAllPreferences() {
        List<Preference> preferences = preferenceRepository.findAll();
        return messageSingleton.ok(Map.of("preferences", preferences));
    }

    public ResponseEntity<?> deletePreference(Long id) {
        Optional<Preference> optionalPreference = preferenceRepository.findById(id);
        if (optionalPreference.isEmpty()) {
            return messageSingleton.preferenceNotFound();
        }
        preferenceRepository.deleteById(id);
        return messageSingleton.ok(Map.of("message", "Preference deleted"));
    }
}
