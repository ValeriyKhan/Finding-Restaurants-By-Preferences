package restaurant.app.merchantPlace;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.app.merchantPlace.branch.Branch;
import restaurant.app.user.User;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
@NoArgsConstructor
@Getter
@Setter
public class MerchantPlace {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Branch> branches;
    private String description;
    @OneToOne
    private User userOwner;
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> usersRatedMerchant;

}
