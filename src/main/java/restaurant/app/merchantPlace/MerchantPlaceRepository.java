package restaurant.app.merchantPlace;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantPlaceRepository extends JpaRepository<MerchantPlace, Long> {
    Optional<MerchantPlace> findByMerchantName(String merchantName);
}
