package jcook.providers;

import jcook.models.Rating;


public class RatingProvider extends AbstractProvider<Rating> {
    private static RatingProvider instance = null;

    private RatingProvider(String databaseName) {
        super(databaseName, "rating", Rating.class);
    }

    public static void initialize(String databaseName) {
        if (instance == null) instance = new RatingProvider(databaseName);
    }

    public static RatingProvider getInstance() {
        return instance;
    }
}
