package valeriy.khan.Finding.Restaurants.By.Preferences.role;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum AppUserPermission implements GrantedAuthority {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete"),
    USER_CHANGE("user:change");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
