package jcook.filters;

import org.bson.conversions.Bson;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CombinedFilter implements RecipeFilter {
    private final Collection<RecipeFilter> filters = new LinkedList<>();
    private final Function<Iterable<Bson>, Bson> combiningFun;

    public CombinedFilter(Function<Iterable<Bson>, Bson> combiningFun) {
        this.combiningFun = combiningFun;
    }

    public boolean addFilter(RecipeFilter filter){
        return filters.add(filter);
    }

    @Override
    public Bson getQuery() {
        return combiningFun.apply(filters.stream().map(RecipeFilter::getQuery).collect(Collectors.toList()));
    }
}
