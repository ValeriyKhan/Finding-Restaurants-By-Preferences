package restaurant.app.preference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.app.user.AppUser;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.*;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Preference {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(unique = true, name = "preference_name")
    private String name;
    @ManyToMany
    private List<AppUser> appUserList;

}