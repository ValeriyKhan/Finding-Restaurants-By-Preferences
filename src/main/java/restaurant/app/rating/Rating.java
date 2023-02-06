package restaurant.app.rating;

import lombok.*;
import restaurant.app.merchantPlace.MerchantPlace;
import restaurant.app.merchantPlace.branch.Branch;
import restaurant.app.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Rating {
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
