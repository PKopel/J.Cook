package jcook.filters;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class IdFilter implements Filter {
    private final Collection<ObjectId> ids = new LinkedList<>();

    public IdFilter(Collection<ObjectId> ids) {
        this.ids.addAll(ids);
    }

    public IdFilter(ObjectId id) { this.ids.add(id); }

    @Override
    public Bson getQuery() {
        return Filters.or(ids.stream()
                .map(id -> Filters.eq("_id", id))
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Id("+ids+")";
    }
}
