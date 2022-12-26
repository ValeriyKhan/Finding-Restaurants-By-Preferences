package restaurant.app.threadLocalSingleton;

import lombok.NoArgsConstructor;
import restaurant.app.user.User;

@NoArgsConstructor
public class ThreadLocalSingleton {
    private final static ThreadLocal<User> USER = ThreadLocal.withInitial(User::new);

    public static void setUser(User user) {
        ThreadLocalSingleton.USER.set(user);
    }

    public static User getUser() {
        return USER.get();
    }
}