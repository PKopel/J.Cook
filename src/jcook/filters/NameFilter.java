package jcook.filters;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

public class NameFilter implements Filter {
    private final String name;

    public NameFilter(String name) {
        this.name = name;
    }

    @Override
    public Bson getQuery() {
        return Filters.regex("name", name);
    }

    @Override
    public String toString() {
        return "Name(" + name + ")";
    }
}
