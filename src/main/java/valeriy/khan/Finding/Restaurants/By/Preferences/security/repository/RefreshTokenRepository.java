package valeriy.khan.Finding.Restaurants.By.Preferences.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import valeriy.khan.Finding.Restaurants.By.Preferences.security.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
}
