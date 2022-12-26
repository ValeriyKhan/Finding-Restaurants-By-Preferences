package restaurant.app.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsernameAndPhoneNumber(String userName, String phoneNumber);

    Optional<User> findByUsername(String username);
}
