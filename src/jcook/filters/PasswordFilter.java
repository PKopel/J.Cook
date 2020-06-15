package jcook.filters;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

public class PasswordFilter implements Filter {
    private final String password;

    public PasswordFilter(String password) {
        this.password = password;
    }

    @Override
    public Bson getQuery() {
        return Filters.regex("password", password);
    }

    @Override
    public String toString() {
        return "Password("+password+")";
    }
}
