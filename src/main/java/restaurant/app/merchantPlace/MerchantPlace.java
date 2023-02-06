package restaurant.app.merchantPlace;

import lombok.*;
import restaurant.app.merchantPlace.branch.Branch;
import restaurant.app.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MerchantPlace {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(mappedBy = "merchantPlace")
    private List<Branch> branches = new ArrayList<>();
    @Column(unique = true)
    private String merchantName;
    private String description;
    @OneToOne
    private User owner;
    private double overallRating;
}
