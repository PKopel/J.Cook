package jcook.filters;

import com.mongodb.client.model.Filters;
import jcook.models.Category;
import org.bson.conversions.Bson;

public class CategoryFilter implements Filter {
    private final Category category;

    public CategoryFilter(Category category) {
        this.category = category;
    }

    @Override
    public Bson getQuery() {
        return Filters.regex("categories", category.name());
    }
}
