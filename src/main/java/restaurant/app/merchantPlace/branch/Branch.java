package restaurant.app.merchantPlace.branch;

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
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long overallScore;
    @Column(unique = true)
    private String branchName;
    @OneToOne
    private User branchOwner;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Preference> preferenceEntities;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> userList;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Rating> ratingList;
    private String address;
    private double latitude;
    private double longitude;

    public void calculateOverallRating() {
        this.overallScore = (this.ratingList.stream().mapToLong(Rating::getScore).sum()) / this.ratingList.size();
    }
}
