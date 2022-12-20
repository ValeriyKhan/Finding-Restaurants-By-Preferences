package restaurant.app.merchantPlace;

import lombok.Getter;
import lombok.Setter;
import restaurant.app.preference.Preference;
import restaurant.app.user.AppUser;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
@Getter
@Setter
public class MerchantPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long rating;
    @OneToOne
    private AppUser MerchantPlaceOwner;
    @ManyToMany
    private List<Preference> ListOfPreferences;
    @ManyToMany
    private List<AppUser> appUserList;

}
