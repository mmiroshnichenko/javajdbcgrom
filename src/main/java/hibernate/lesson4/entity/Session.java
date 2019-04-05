package hibernate.lesson4.entity;

public class Session {
    private static User user;

    public static void addUser(User newUser) {
        user = newUser;
    }

    public static void clear() {
        user = null;
    }

    public static User getUser() {
        return user;
    }
}
