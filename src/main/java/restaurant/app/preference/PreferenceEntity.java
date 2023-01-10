package restaurant.app.preference;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class PreferenceEntity {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Preference preference;
    public PreferenceEntity(Preference preference) {
        this.preference = preference;
    }
}
