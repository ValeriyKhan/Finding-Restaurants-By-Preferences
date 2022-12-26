package restaurant.app.security;


import lombok.Getter;
import lombok.Setter;
import restaurant.app.user.User;

import javax.persistence.*;

@Table(name = "refresh_token")
@Entity
@Setter
@Getter
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 999)
    private String token;
    @OneToOne
    private User user;
}
