package jcook.filters;

import org.bson.conversions.Bson;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CombinedFilter implements Filter {
    private final Collection<Filter> filters = new LinkedList<>();
    private final Function<Iterable<Bson>, Bson> combiningFun;

    public CombinedFilter(Function<Iterable<Bson>, Bson> combiningFun) {
        this.combiningFun = combiningFun;
    }

    public boolean addFilter(Filter filter) {
        return filters.add(filter);
    }

    @Override
    public Bson getQuery() {
        return combiningFun.apply(filters.stream().map(Filter::getQuery).collect(Collectors.toList()));
    }
}
