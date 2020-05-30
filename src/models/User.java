package models;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.util.Collection;

public class User extends BasicDBObject {
    ObjectId id;
    String name;
    Collection<Recipe> recipes;
    Collection<Rating> ratings;
}
