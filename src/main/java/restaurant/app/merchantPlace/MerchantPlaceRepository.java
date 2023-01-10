package restaurant.app.merchantPlace;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.app.preference.Preference;
import restaurant.app.preference.PreferenceEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MerchantPlaceRepository extends JpaRepository<MerchantPlace, Long> {
    Set<MerchantPlace> findMerchantPlacesByPreferenceEntitiesIn(List<PreferenceEntity> preferenceEntities);
    Optional<MerchantPlace> findByMerchantName(String merchantName);
}
