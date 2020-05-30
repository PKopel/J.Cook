package providers;

import models.Rating;

import java.util.Date;

public class RatingProvider extends AbstractProvider<Rating>{

    public RatingProvider(String databaseName) {
        super(databaseName,"rating", Rating.class);
    }

    public static void main(String[] args) {
        RatingProvider ratingProvider = new RatingProvider("JCookTest");
        Rating rating = new Rating(3, "rating", new Date());
        ratingProvider.addObject(rating);
    }
}
