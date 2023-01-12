package restaurant.app.preference;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Preference {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private PreferenceE preferenceE;
    public Preference(PreferenceE preferenceE) {
        this.preferenceE = preferenceE;
    }
}
