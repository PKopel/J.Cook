package models;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.util.Date;

public class Rating {
    private int stars;
    private String description;
    private Date date;

    public Rating(){}

    public Rating( int stars, String description, Date date) {
        this.stars = stars;
        this.description = description;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
