package restaurant.app.merchantPlace;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.app.preference.Preference;

import java.util.List;
import java.util.Optional;

public interface MerchantPlaceRepository extends JpaRepository<MerchantPlace, Long> {
    List<MerchantPlace> findMerchantPlacesByPreferencesIn(List<Preference> preferences);
    Optional<MerchantPlace> findByMerchantName(String merchantName);
}
