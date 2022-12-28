package restaurant.app.role;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum UserPermission implements GrantedAuthority {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete"),
    USER_CHANGE("user:change"),
    MERCHANT_READ("merchant:read"),
    MERCHANT_WRITE("merchant:write"),
    MERCHANT_DELETE("merchant:delete"),
    MERCHANT_CHANGE("merchant:change"),
    PREFERENCE_WRITE("preference:write"),
    PREFERENCE_READ("preference:read"),
    PREFERENCE_DELETE("preference:delete");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
