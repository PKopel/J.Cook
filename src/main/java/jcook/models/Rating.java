package jcook.models;

import org.bson.types.ObjectId;

public class Rating {
    private double stars;
    private String description;
    private String date;
    private ObjectId author;

    public Rating() {
    }

    public Rating(int stars, String description, String date, ObjectId author) {
        this.stars = stars;
        this.description = description;
        this.date = date;
        this.author = author;
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

    public ObjectId getAuthor() {
        return author;
    }

    public void setAuthor(ObjectId
                                  author) {
        this.author = author;
    }
}
