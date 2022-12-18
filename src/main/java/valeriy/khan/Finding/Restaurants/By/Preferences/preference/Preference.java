package valeriy.khan.Finding.Restaurants.By.Preferences.preference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import valeriy.khan.Finding.Restaurants.By.Preferences.MerchantPlace.MerchantPlace;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.AppUser;

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
    @ManyToMany
    private List<MerchantPlace> merchantPlaceList;

}
