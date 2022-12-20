package restaurant.app.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import restaurant.app.role.AppUserRole;
import restaurant.app.user.status.AppUserStatus;
import restaurant.app.user.type.AppUserType;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static restaurant.app.user.status.AppUserStatus.*;

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
    private String salt;
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
