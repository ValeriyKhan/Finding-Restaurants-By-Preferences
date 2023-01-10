package restaurant.app.merchantPlace;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.dto.*;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.preference.PreferenceEntity;
import restaurant.app.preference.PreferenceRepository;
import restaurant.app.preference.PreferenceService;
import restaurant.app.threadLocalSingleton.ThreadLocalSingleton;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MerchantPlaceService {
    private final MerchantPlaceRepository merchantPlaceRepository;
    private final PreferenceRepository preferenceRepository;
    private final MessageSingleton messageSingleton;
    private final PreferenceService preferenceService;

    public ResponseEntity<?> createMerchantPlace(CreateMerchantPlaceRequest createMerchantPlaceRequest) {
        Optional<MerchantPlace> optionalMerchantPlace = merchantPlaceRepository
                .findByMerchantName(createMerchantPlaceRequest.getMerchantName());
        if (optionalMerchantPlace.isPresent()) {
            return messageSingleton.merchantAlreadyExists();
        }
        MerchantPlace merchantPlace = MerchantPlace.builder()
                .merchantName(createMerchantPlaceRequest.getMerchantName())
                .merchantPlaceOwner(ThreadLocalSingleton.getUser())
                .address(createMerchantPlaceRequest.getAddress())
                .build();
        merchantPlaceRepository.save(merchantPlace);
        CreateMerchantPlaceResponse response = CreateMerchantPlaceResponse.builder()
                .id(merchantPlace.getId())
                .merchantName(createMerchantPlaceRequest.getMerchantName())
                .merchantPlaceOwner(ThreadLocalSingleton.getUser().getUsername())
                .address(createMerchantPlaceRequest.getAddress())
                .build();
        return messageSingleton.ok(Map.of("merchantPlace", response));
    }

    public ResponseEntity<?> getMerchantPlace(Long id) {
        Optional<MerchantPlace> optionalMerchantPlace = merchantPlaceRepository.findById(id);
        if (optionalMerchantPlace.isEmpty()) {
            return messageSingleton.merchantNotFound();
        }
        MerchantPlace merchantPlace = optionalMerchantPlace.get();
        CreateMerchantPlaceResponse response = CreateMerchantPlaceResponse.builder()
                .id(merchantPlace.getId())
                .merchantName(merchantPlace.getMerchantName())
                .merchantPlaceOwner(ThreadLocalSingleton.getUser().getUsername())
                .address(merchantPlace.getAddress())
                .preferenceEntities(merchantPlace.getPreferenceEntities())
                .build();
        return messageSingleton.ok(Map.of("MerchantPlace", response));
    }

    public ResponseEntity<?> getAllMerchantPlaces(int page, int size) {
        Page<MerchantPlace> merchantPlaceList = merchantPlaceRepository.findAll(PageRequest.of(page, size));
        return messageSingleton.ok(Map.of("MerchantPlaces", merchantPlaceList));
    }

    public ResponseEntity<?> changeMerchantPlace(ChangeMerchantPlace changeMerchantPlace) {
        Optional<MerchantPlace> optionalMerchantPlace = merchantPlaceRepository.findById(changeMerchantPlace.getId());
        if (optionalMerchantPlace.isEmpty()) {
            return messageSingleton.merchantNotFound();
        }
        MerchantPlace merchantPlace = optionalMerchantPlace.get();
        merchantPlace.setMerchantName(changeMerchantPlace.getMerchantName());
        merchantPlace.setPreferenceEntities(changeMerchantPlace.getPreferenceEntities());
        merchantPlace.setAddress(changeMerchantPlace.getAddress());
        merchantPlaceRepository.save(merchantPlace);
        return messageSingleton.ok(Map.of("message: User changed", merchantPlace));
    }

    public ResponseEntity<?> setPreferencesToMerchantPlace(SetPreferencesToMerchantPlaceRequest setPreferencesToMerchantPlaceRequest) {
        Optional<MerchantPlace> optionalMerchantPlace = merchantPlaceRepository
                .findById(setPreferencesToMerchantPlaceRequest.getMerchantPlaceId());
        if (optionalMerchantPlace.isEmpty()) {
            return messageSingleton.merchantNotFound();
        }
        List<PreferenceEntity> preferencesToSetToEntity = preferenceService.matchPreferences(setPreferencesToMerchantPlaceRequest.getPreferenceIdList());
        MerchantPlace merchantPlace = optionalMerchantPlace.get();
        merchantPlace.setPreferenceEntities(preferencesToSetToEntity);
        merchantPlaceRepository.save(merchantPlace);
        return messageSingleton.ok(Map.of("preferences", SetPreferencesToMerchantPlaceResponse.builder()
                .merchantPlaceId(merchantPlace.getId())
                .merchantPlaceName(merchantPlace.getMerchantName())
                .preferenceEntityListOfMerchantPlace(preferencesToSetToEntity)
                .build()));
    }
}
