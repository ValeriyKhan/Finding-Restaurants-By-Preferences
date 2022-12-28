package restaurant.app.preference;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Optional<Preference> findPreferenceByName(String name);

    List<Preference> findPreferenceByNameIn(List<Preference> preferences);
}
