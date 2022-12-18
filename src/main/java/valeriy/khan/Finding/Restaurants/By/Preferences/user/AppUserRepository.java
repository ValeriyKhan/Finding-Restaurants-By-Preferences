package valeriy.khan.Finding.Restaurants.By.Preferences.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
    Optional<AppUser> findByUsernameAndPhoneNumber(String userName, String phoneNumber);

    Optional<AppUser> findByUsername(String username);
}
