package restaurant.app.langMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LangMessageRepository extends JpaRepository<LangMessage, Long> {
    Optional<LangMessage> findByKeyAndLang(String key, LanguageEnum lang);

    Page<LangMessage> findAllByLang(Pageable pageable, LanguageEnum lang);

}
