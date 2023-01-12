package restaurant.app.preference;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.preference.dto.CreatePreferenceRequest;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;
    private final MessageSingleton messageSingleton;

    public ResponseEntity<?> createPreference(CreatePreferenceRequest createPreferenceRequest) {
        Optional<Preference> optionalPreference = preferenceRepository.findPreferenceEntityByPreferenceE(createPreferenceRequest.getPreferenceE());
        if (optionalPreference.isPresent()) {
            return messageSingleton.preferenceAlreadyExists();
        }
        Preference preference = new Preference(createPreferenceRequest.getPreferenceE());
        preferenceRepository.save(preference);
        return messageSingleton.ok(Map.of("preference", preference));
    }

    public ResponseEntity<?> getAllPreferences() {
        List<Preference> preferenceEntities = getPreferenceListFromDb();
        return messageSingleton.ok(Map.of("preferences", preferenceEntities));
    }

    public List<Preference> getPreferenceListFromDb() {
        return preferenceRepository.findAll();
    }

    public ResponseEntity<?> deletePreference(Long id) {
        Optional<Preference> optionalPreference = preferenceRepository.findById(id);
        if (optionalPreference.isEmpty()) {
            return messageSingleton.preferenceNotFound();
        }
        preferenceRepository.deleteById(id);
        return messageSingleton.ok(Map.of("message", "Preference deleted"));
    }

    public List<Preference> matchPreferences(List<Long> preferenceIdFromRequest) {
        List<Preference> preferenceFromDBList = preferenceRepository.findAll();
        List<Preference> preferencesToResponse = new ArrayList<>();
        for (Preference prefIdFromDb : preferenceFromDBList) {
            for (Long prefIdFromRequest : preferenceIdFromRequest) {
                if (Objects.equals(prefIdFromDb.getId(), prefIdFromRequest)) {
                    preferencesToResponse.add(prefIdFromDb);
                }
            }
        }
        return preferencesToResponse;
    }
}
