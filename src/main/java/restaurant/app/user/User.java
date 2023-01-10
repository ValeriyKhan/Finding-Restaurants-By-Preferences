package restaurant.app.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import restaurant.app.merchantPlace.MerchantPlace;
import restaurant.app.preference.PreferenceEntity;
import restaurant.app.rating.Rating;
import restaurant.app.role.UserRole;
import restaurant.app.user.status.UserStatus;
import restaurant.app.user.type.UserType;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static restaurant.app.user.status.UserStatus.*;

@Table(name = "user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements UserDetails {
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
    private String salt;
    @Column(unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    @Enumerated(STRING)
    private UserType type;
    @Enumerated(STRING)
    private UserStatus status;
    @Enumerated(STRING)
    private UserRole role;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<PreferenceEntity> preferenceEntityList;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Rating> ratingList;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<MerchantPlace> merchantPlaceList;

    public boolean isBlocked() {
        return this.status.equals(BLOCKED);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getUserPermissionSet().stream()
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
