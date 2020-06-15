package jcook.loginManager;

import com.mongodb.client.model.Filters;
import jcook.filters.CombinedFilter;
import jcook.filters.NameFilter;
import jcook.filters.PasswordFilter;
import jcook.models.User;
import jcook.providers.UserProvider;

import java.io.IOException;
import java.util.List;

public class LoginManager {

    private static boolean offline = false;
    private static User currentUser = null;

    public static void logOut() {
        offline = false;
        currentUser = null;
    }

    public static boolean joinOffline() {
        if(offline == true || currentUser != null) {
            System.out.println("User already logged in");
            return false;
        }
        offline = true;
        return true;
    }

    public static boolean logIn(String username, String password) {
        if(offline == true || currentUser != null) {
            System.out.println("User already logged in");
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

    public static boolean offlineSession() {
        return offline;
    }

    public static User getLoggedUser() throws IOException {
        if(offline) {
            return User.offlineUser();
        } else {
            return currentUser;
        }
    }
}
