package valeriy.khan.Finding.Restaurants.By.Preferences.security;


import lombok.Getter;
import lombok.Setter;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.AppUser;

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
    private AppUser appUser;
}
