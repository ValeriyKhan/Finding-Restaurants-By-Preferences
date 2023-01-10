package restaurant.app.threadLocalSingleton;

import lombok.NoArgsConstructor;
import restaurant.app.langMessage.Lang;
import restaurant.app.user.User;

@NoArgsConstructor
public class ThreadLocalSingleton {
    private final static ThreadLocal<User> USER = ThreadLocal.withInitial(User::new);
    private final static ThreadLocal<Lang> LANG = ThreadLocal.withInitial(Lang::new);

    public static void setUser(User user) {
        ThreadLocalSingleton.USER.set(user);
    }

    public static User getUser() {
        return USER.get();
    }

    public static void setLang(Lang lang) {
        ThreadLocalSingleton.LANG.set(lang);
    }

    public static Lang getLang() {
        return LANG.get();
    }
}