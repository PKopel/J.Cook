package jcook.filters;

import org.bson.conversions.Bson;

public interface Filter {
    Bson getQuery();
}
