package restaurant.app.rating;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import restaurant.app.merchantPlace.MerchantPlace;
import restaurant.app.merchantPlace.branch.Branch;
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
    @OneToOne
    private User user;
    @OneToOne
    private Branch branch;
    private int score;
}
