package restaurant.app.role;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

@Getter
public enum UserRole {
    USER(Sets.newHashSet()),

    MERCHANT_OWNER(Sets.newHashSet(
            UserPermission.MERCHANT_READ,
            UserPermission.MERCHANT_WRITE,
            UserPermission.MERCHANT_CHANGE
    )),
    ADMIN(Sets.newHashSet(
            UserPermission.USER_READ,
            UserPermission.USER_WRITE,
            UserPermission.USER_CHANGE,
            UserPermission.USER_DELETE,
            UserPermission.MERCHANT_READ,
            UserPermission.MERCHANT_WRITE,
            UserPermission.MERCHANT_CHANGE,
            UserPermission.MERCHANT_DELETE
    )),
    MODERATOR(Sets.newHashSet(
            UserPermission.USER_READ,
            UserPermission.USER_WRITE,
            UserPermission.USER_CHANGE,
            UserPermission.MERCHANT_READ,
            UserPermission.MERCHANT_WRITE,
            UserPermission.MERCHANT_CHANGE
    ));
    private final Set<UserPermission> userPermissionSet;

    UserRole(Set<UserPermission> userPermissionSet) {
        this.userPermissionSet = userPermissionSet;
    }

}
