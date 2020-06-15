package jcook.loginManager;

import com.mongodb.client.model.Filters;
import jcook.filters.CombinedFilter;
import jcook.filters.NameFilter;
import jcook.filters.PasswordFilter;
import jcook.models.User;
import jcook.providers.UserProvider;

import java.util.List;

public class LoginManager {

    private static boolean offline = false;
    static private User currentUser = null;

    static void logOut() {
        offline = false;
        currentUser = null;
    }

    static void joinOffline() {
        offline= true;
    }

    static boolean logIn(String username, String password) {
        if(currentUser != null) {
            System.out.println("Player already logged in");
            return false;
        }
        CombinedFilter userFilter = new CombinedFilter(Filters::and);
        userFilter.addFilter(new NameFilter(username));
        userFilter.addFilter(new PasswordFilter(password));
        List<User> matchingUsers = UserProvider.getInstance().getObjects(userFilter);
        if(matchingUsers.size() > 1) {
            throw new IllegalStateException("Database should not contain for ");
        } else if(matchingUsers.size() == 0) {
            System.out.println("Wrong username and/or password");
            return false;
        } else {
            currentUser = matchingUsers.get(0);
            return true;
        }
    }

    static User getLoggedUser() {
        if(offline) {
            return new User("Offline", null, null);
        } else {
            return currentUser;
        }
    }
}
