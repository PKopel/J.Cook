package jcook.filters;

import org.bson.conversions.Bson;

public interface Filter {
    default Bson getQuery() {
        return null;
    }
}
