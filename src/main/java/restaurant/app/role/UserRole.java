package restaurant.app.role;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

@Getter
public enum AppUserRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(AppUserPermission.USER_READ, AppUserPermission.USER_WRITE, AppUserPermission.USER_CHANGE, AppUserPermission.USER_DELETE)),
    MODERATOR(Sets.newHashSet(AppUserPermission.USER_READ, AppUserPermission.USER_WRITE, AppUserPermission.USER_CHANGE));
    private final Set<AppUserPermission> appUserPermissionSet;

    AppUserRole(Set<AppUserPermission> appUserPermissionSet) {
        this.appUserPermissionSet = appUserPermissionSet;
    }

}
