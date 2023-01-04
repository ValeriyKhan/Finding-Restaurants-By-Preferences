package restaurant.app.langMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.app.langMessage.dto.CreateMessageRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/lang-message")
@RequiredArgsConstructor
public class LangMessageController {
    private final LangMessageService langMessageService;

    @PostMapping
    public ResponseEntity<?> createMessage(
            @Valid @RequestBody CreateMessageRequest createMessageRequest
    ) {
        return langMessageService.createMessage(createMessageRequest);
    }

    @GetMapping
    public ResponseEntity<?> getAllMessages(
            @RequestParam("size") int size,
            @RequestParam("page") int page
    ) {
        return langMessageService.getAllMessages(size, page);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteMessage(
            @RequestParam("id") Long id
    ){
        return langMessageService.deleteLangMessage(id);
    }
}
