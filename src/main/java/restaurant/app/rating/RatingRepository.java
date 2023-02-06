package restaurant.app.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.app.merchantPlace.branch.Branch;
import restaurant.app.user.User;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndBranch(User user, Branch branch);

    List<Rating> findByBranch(Branch branch);
}
