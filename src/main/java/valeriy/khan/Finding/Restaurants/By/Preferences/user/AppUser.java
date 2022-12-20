package valeriy.khan.Finding.Restaurants.By.Preferences.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import valeriy.khan.Finding.Restaurants.By.Preferences.preference.Preference;
import valeriy.khan.Finding.Restaurants.By.Preferences.MerchantPlace.MerchantPlace;
import valeriy.khan.Finding.Restaurants.By.Preferences.rating.Rating;
import valeriy.khan.Finding.Restaurants.By.Preferences.role.AppUserRole;
import valeriy.khan.Finding.Restaurants.By.Preferences.security.RefreshTokenEntity;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.status.AppUserStatus;
import valeriy.khan.Finding.Restaurants.By.Preferences.user.type.AppUserType;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static valeriy.khan.Finding.Restaurants.By.Preferences.user.status.AppUserStatus.*;

@Table(name = "user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Timestamp dateOfBirth;
    @Column(unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    @Enumerated(STRING)
    private AppUserType type;
    @Enumerated(STRING)
    private AppUserStatus status;
    @Enumerated(STRING)
    private AppUserRole role;

    public boolean isBlocked() {
        return this.status.equals(BLOCKED);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAppUserPermissionSet().stream()
                .map(appUserPermission -> new SimpleGrantedAuthority(appUserPermission.getAuthority()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.status.equals(ACTIVE);
    }

}
