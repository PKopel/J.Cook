package providers;

import models.Rating;
import models.User;

import java.util.Date;
import java.util.LinkedList;

public class UserProvider extends AbstractProvider<User>{

    public UserProvider(String databaseName) {
        super(databaseName, "user", User.class);
    }


    public static void main(String[] args) {
        UserProvider ratingProvider = new UserProvider("JCookTest");
        User user = new User("user", new LinkedList<>(), new LinkedList<>());
        ratingProvider.addObject(user);
    }
}
