package jcook.filters;

import org.bson.conversions.Bson;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CombinedFilter implements Filter {
    private final List<Filter> filters = new LinkedList<>();
    private final Function<Iterable<Bson>, Bson> combiningFun;

    public CombinedFilter(Function<Iterable<Bson>, Bson> combiningFun) {
        this.combiningFun = combiningFun;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    public List<Filter> getFilters() {
        return filters;
    }

    @Override
    public Bson getQuery() {
        return filters.isEmpty() ? null :
                combiningFun.apply(filters.stream().map(Filter::getQuery).collect(Collectors.toList()));
    }

}
