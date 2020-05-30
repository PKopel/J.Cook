package providers;

import models.Rating;

public class RatingProvider extends AbstractProvider<Rating> {
    public RatingProvider(String databaseName) {
        super(databaseName, "rating", Rating.class);
    }
}
