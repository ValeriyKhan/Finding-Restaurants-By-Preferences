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
        Optional<PreferenceEntity> optionalPreference = preferenceRepository.findPreferenceEntityByPreference(createPreferenceRequest.getPreference());
        if (optionalPreference.isPresent()) {
            return messageSingleton.preferenceAlreadyExists();
        }
        PreferenceEntity preferenceEntity = new PreferenceEntity(createPreferenceRequest.getPreference());
        preferenceRepository.save(preferenceEntity);
        return messageSingleton.ok(Map.of("preference", preferenceEntity));
    }

    public ResponseEntity<?> getAllPreferences() {
        List<PreferenceEntity> preferenceEntities = getPreferenceListFromDb();
        return messageSingleton.ok(Map.of("preferences", preferenceEntities));
    }

    public List<PreferenceEntity> getPreferenceListFromDb() {
        return preferenceRepository.findAll();
    }

    public ResponseEntity<?> deletePreference(Long id) {
        Optional<PreferenceEntity> optionalPreference = preferenceRepository.findById(id);
        if (optionalPreference.isEmpty()) {
            return messageSingleton.preferenceNotFound();
        }
        preferenceRepository.deleteById(id);
        return messageSingleton.ok(Map.of("message", "Preference deleted"));
    }

    public List<PreferenceEntity> matchPreferences(List<Long> preferenceIdFromRequest) {
        List<PreferenceEntity> preferenceEntityFromDBList = preferenceRepository.findAll();
        List<PreferenceEntity> preferencesToResponse = new ArrayList<>();
        for (PreferenceEntity prefIdFromDb : preferenceEntityFromDBList) {
            for (Long prefIdFromRequest : preferenceIdFromRequest) {
                if (Objects.equals(prefIdFromDb.getId(), prefIdFromRequest)) {
                    preferencesToResponse.add(prefIdFromDb);
                }
            }
        }
        return preferencesToResponse;
    }
}
