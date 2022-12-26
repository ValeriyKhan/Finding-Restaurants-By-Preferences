package restaurant.app.rating;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import restaurant.app.merchantPlace.MerchantPlace;
import restaurant.app.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@RequiredArgsConstructor
@Getter
@Setter
public class Rating {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long stars;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> userList;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<MerchantPlace> merchantPlaceList;

}
