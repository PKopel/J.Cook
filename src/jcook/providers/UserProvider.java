package jcook.providers;

import jcook.models.User;

public class UserProvider extends AbstractProvider<User> {
    private static UserProvider instance = null;

    private UserProvider(String databaseName) {
        super(databaseName, "user", User.class);
    }

    public static void initialize(String databaseName) {
        if (instance == null) instance = new UserProvider(databaseName);
    }

    public static UserProvider getInstance() {
        return instance;
    }
}
