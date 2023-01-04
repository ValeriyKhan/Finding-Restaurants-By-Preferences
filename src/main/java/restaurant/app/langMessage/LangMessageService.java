package restaurant.app.langMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurant.app.langMessage.dto.CreateMessageRequest;
import restaurant.app.messagesingleton.MessageSingleton;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LangMessageService {
    private final LangMessageRepository langMessageRepository;
    private final MessageSingleton messageSingleton;

    public ResponseEntity<?> createMessage(CreateMessageRequest createMessageRequest) {
        Optional<LangMessage> optionalLangMessage = langMessageRepository.findByMessageName(createMessageRequest.getMessageName());
        if (optionalLangMessage.isPresent()) {
            return messageSingleton.messageAlreadyExists();
        }
        LangMessage message = LangMessage.builder()
                .messageName(createMessageRequest.getMessageName().toLowerCase())
                .ru(createMessageRequest.getRuMessage())
                .uz(createMessageRequest.getUzMessage())
                .en(createMessageRequest.getEnMessage())
                .build();
        langMessageRepository.save(message);
        return messageSingleton.ok(Map.of("message", message));
    }

    public ResponseEntity<?> getAllMessages(int size, int page) {
        Page<LangMessage> languagePage = langMessageRepository.findAll(PageRequest.of(page, size));
        return messageSingleton.ok(Map.of("messages", languagePage));
    }

    public ResponseEntity<?> deleteLangMessage(Long id) {
        Optional<LangMessage> optionalLangMessage = langMessageRepository.findById(id);
        if (optionalLangMessage.isEmpty()) {
            return messageSingleton.messageDoesNotExist();
        }
        langMessageRepository.deleteById(id);
        return messageSingleton.ok(Map.of("message", "Message with id: " + id + " deleted"));
    }
}
