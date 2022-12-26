package restaurant.app.merchantPlace;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.merchantPlace.dto.ChangeMerchantPlace;
import restaurant.app.merchantPlace.dto.CreateMerchantPlaceRequest;
import restaurant.app.merchantPlace.dto.CreateMerchantPlaceResponse;
import restaurant.app.messagesingleton.MessageSingleton;
import restaurant.app.threadLocalSingleton.ThreadLocalSingleton;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MerchantPlaceService {
    private final MerchantPlaceRepository merchantPlaceRepository;
    private final MessageSingleton messageSingleton;

    public ResponseEntity<?> createMerchantPlace(CreateMerchantPlaceRequest createMerchantPlaceRequest) {
        Optional<MerchantPlace> optionalMerchantPlace = merchantPlaceRepository
                .findByMerchantName(createMerchantPlaceRequest.getMerchantName());
        if (optionalMerchantPlace.isPresent()) {
            return messageSingleton.merchantAlreadyExists();
        }
        MerchantPlace merchantPlace = MerchantPlace.builder()
                .merchantName(createMerchantPlaceRequest.getMerchantName())
                .merchantPlaceOwner(ThreadLocalSingleton.getUser())
                .preferences(createMerchantPlaceRequest.getPreferences())
                .address(createMerchantPlaceRequest.getAddress())
                .build();
        merchantPlaceRepository.save(merchantPlace);
        CreateMerchantPlaceResponse response = CreateMerchantPlaceResponse.builder()
                .id(merchantPlace.getId())
                .merchantName(createMerchantPlaceRequest.getMerchantName())
                .merchantPlaceOwner(ThreadLocalSingleton.getUser())
                .preferences(createMerchantPlaceRequest.getPreferences())
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
                .merchantPlaceOwner(ThreadLocalSingleton.getUser())
                .preferences(merchantPlace.getPreferences())
                .address(merchantPlace.getAddress())
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
        merchantPlace.setPreferences(changeMerchantPlace.getPreferences());
        merchantPlace.setAddress(changeMerchantPlace.getAddress());
        merchantPlaceRepository.save(merchantPlace);
        return messageSingleton.ok(Map.of("message: User changed", merchantPlace));
    }
}
