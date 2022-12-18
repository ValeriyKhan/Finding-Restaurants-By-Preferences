package valeriy.khan.Finding.Restaurants.By.Preferences.MerchantPlace;

import lombok.Getter;
import lombok.Setter;
import valeriy.khan.Finding.Restaurants.By.Preferences.preference.Preference;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.AppUser;

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
