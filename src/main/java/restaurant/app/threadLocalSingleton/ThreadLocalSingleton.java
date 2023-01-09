package restaurant.app.threadLocalSingleton;

import lombok.NoArgsConstructor;
import restaurant.app.langMessage.Language;
import restaurant.app.user.User;

@NoArgsConstructor
public class ThreadLocalSingleton {
    private final static ThreadLocal<User> USER = ThreadLocal.withInitial(User::new);
    private final static ThreadLocal<Language> LANG = ThreadLocal.withInitial(Language::new);

    public static void setUser(User user) {
        ThreadLocalSingleton.USER.set(user);
    }

    public static User getUser() {
        return USER.get();
    }

    public static void setLang(Language lang) {
        ThreadLocalSingleton.LANG.set(lang);
    }

    public static Language getLang() {
        return LANG.get();
    }
}