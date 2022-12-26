package restaurant.app.merchantPlace;

import lombok.*;
import restaurant.app.preference.Preference;
import restaurant.app.rating.Rating;
import restaurant.app.user.User;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long overallRating;
    @Column(unique = true)
    private String merchantName;
    @OneToOne
    private User merchantPlaceOwner;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Preference> preferences;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> userList;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Rating> ratingList;
    private String address;
    private String latitude;
    private String longitude;

    public void calculateOverallRating() {
        this.overallRating = (this.ratingList.stream().mapToLong(Rating::getStars).sum()) / this.ratingList.size();
    }
}
