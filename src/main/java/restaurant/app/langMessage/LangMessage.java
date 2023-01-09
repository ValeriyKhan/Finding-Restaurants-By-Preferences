package restaurant.app.langMessage;

import lombok.*;

import javax.persistence.*;


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
    private String key;
    @Enumerated(EnumType.STRING)
    private LanguageEnum lang;
    private String message;

}
