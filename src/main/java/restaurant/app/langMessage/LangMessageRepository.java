package restaurant.app.langMessage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LangMessageRepository extends JpaRepository<LangMessage, Long> {

    Optional<LangMessage> findByMessageName(String messageName);
}
