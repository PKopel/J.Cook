package jcook.authentication;

import jcook.filters.NameFilter;
import jcook.models.User;
import jcook.providers.UserProvider;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;

public class LoginManager {

    private static LoginManager instance = null;
    private final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    private boolean offline = false;
    private User currentUser = null;

    private LoginManager() throws NoSuchAlgorithmException {
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            try {
                instance = new LoginManager();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void logOut() {
        offline = false;
        currentUser = null;
    }

    public boolean joinOffline() {
        if (offline || currentUser != null) {
            System.out.println("User already logged in");
            return false;
        }
        offline = true;
        return true;
    }

    public boolean logIn(String username, String password) {
        if (offline || currentUser != null) {
            System.out.println("User already logged in");
            return false;
        }
        List<User> matchingUsers = UserProvider.getInstance().getObjects(new NameFilter(username));
        if (matchingUsers.size() > 1) {
            throw new IllegalStateException("Database should not contain more than one user with the same name ");
        } else if (matchingUsers.size() == 0) {
            System.out.println("Wrong username");
            return false;
        } else {
            User match = matchingUsers.get(0);
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), match.getSalt(), 65536, 128);
            try {
                if (Arrays.equals(match.getPassword(), keyFactory.generateSecret(keySpec).getEncoded())) {
                    currentUser = match;
                    return true;
                } else {
                    return false;
                }
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean offlineSession() {
        return offline;
    }

    public User getLoggedUser() throws IOException {
        if (offline) {
            return User.offlineUser();
        } else {
            return currentUser;
        }
    }
}
