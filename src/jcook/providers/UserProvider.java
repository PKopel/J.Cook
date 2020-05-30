package jcook.providers;

import jcook.models.User;

public class UserProvider extends AbstractProvider<User> {
    public UserProvider(String databaseName) {
        super(databaseName, "user", User.class);
    }
}
