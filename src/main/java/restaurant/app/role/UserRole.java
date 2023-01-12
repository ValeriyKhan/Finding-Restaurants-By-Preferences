package restaurant.app.role;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

import static restaurant.app.role.UserPermission.*;

@Getter
public enum UserRole {
    USER(Sets.newHashSet()),

    MERCHANT_OWNER(Sets.newHashSet(
            MERCHANT_READ,
            MERCHANT_WRITE,
            MERCHANT_CHANGE
    )),
    ADMIN(Sets.newHashSet(
            USER_READ,
            USER_WRITE,
            USER_CHANGE,
            USER_DELETE,
            MERCHANT_READ,
            MERCHANT_WRITE,
            MERCHANT_CHANGE,
            MERCHANT_DELETE,
            FILIAL_READ,
            FILIAL_WRITE,
            FILIAL_CHANGE,
            FILIAL_DELETE,
            PREFERENCE_READ,
            PREFERENCE_WRITE,
            PREFERENCE_DELETE,
            LANG_MESSAGE_WRITE,
            LANG_MESSAGE_READ
    )),
    MODERATOR(Sets.newHashSet(
            USER_READ,
            USER_WRITE,
            USER_CHANGE,
            MERCHANT_READ,
            MERCHANT_WRITE,
            MERCHANT_CHANGE,
            FILIAL_READ,
            FILIAL_WRITE,
            FILIAL_CHANGE,
            PREFERENCE_READ,
            LANG_MESSAGE_READ
    ));
    private final Set<UserPermission> userPermissionSet;

    UserRole(Set<UserPermission> userPermissionSet) {
        this.userPermissionSet = userPermissionSet;
    }

}
