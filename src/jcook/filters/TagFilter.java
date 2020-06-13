package jcook.filters;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

public class TagFilter implements Filter {
    private final String tag;

    public TagFilter(String tag) {
        this.tag = tag;
    }

    @Override
    public Bson getQuery() {
        return Filters.regex("tags", tag);
    }

    @Override
    public String toString() {
        return "Tag("+tag+")";
    }
}
