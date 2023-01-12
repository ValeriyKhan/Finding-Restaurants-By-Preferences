package restaurant.app.merchantPlace.branch;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.app.preference.Preference;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Set<Branch> findBranchByPreferenceEntitiesIn(List<Preference> preferenceEntities);
    Optional<Branch> findByBranchName(String branchName);
}
