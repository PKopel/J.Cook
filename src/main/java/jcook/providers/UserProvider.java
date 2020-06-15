package jcook.providers;

import jcook.models.User;

public class UserProvider extends AbstractProvider<User> {
    private static UserProvider instance = null;

    private UserProvider(String connectionName, String databaseName) {
        super(connectionName, databaseName, "user", User.class);
    }

    public static void initialize(String connectionName, String databaseName) {
        if (instance == null) instance = new UserProvider(connectionName, databaseName);
        else System.out.println("UserProvider already initialized");
    }

    public static UserProvider getInstance() {
        return instance;
    }
}
