package jcook.models;

import java.util.Date;

public class Rating {
    private double stars;
    private String description;
    private String date;

    public Rating() {
    }

    public Rating(int stars, String description, String date) {
        this.stars = stars;
        this.description = description;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }
}
