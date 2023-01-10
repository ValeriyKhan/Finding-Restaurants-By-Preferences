package restaurant.app.langMessage;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LangMessage {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(STRING)
    private MessageKey key;
    @Enumerated(STRING)
    private Language lang;
    private String message;

}
