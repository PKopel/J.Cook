package models;

import java.util.Date;

public class Rating {
    String id;
    int stars;
    String description;
    Date date;

    public Rating(String id, int stars, String description, Date date) {
        this.id = id;
        this.stars = stars;
        this.description = description;
        this.date = date;
    }
}
