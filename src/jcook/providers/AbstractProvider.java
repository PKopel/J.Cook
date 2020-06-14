package jcook.providers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import jcook.filters.Filter;
import jcook.filters.IdFilter;
import jcook.models.Category;
import jcook.models.Model;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class AbstractProvider<T extends Model> {
    protected final MongoDatabase db;
    private final String collectionName;
    private final Class<T> clazz;

    protected AbstractProvider(String connectionName, String databaseName, String collectionName, Class<T> clazz) {
        this.collectionName = collectionName;
        this.clazz = clazz;
        ConnectionString connection = new ConnectionString(connectionName);
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connection)
                .codecRegistry(pojoCodecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        db = mongoClient.getDatabase(databaseName);
    }

    public List<T> getObjects(Filter filter) {
        MongoCollection<T> collection = db.getCollection(collectionName, clazz);
        return (filter.getQuery() != null ?
                collection.find(filter.getQuery()) : collection.find())
                .into(new LinkedList<>());
    }

    public void addObject(T object) {
        db.getCollection(collectionName, clazz)
                .insertOne(object);
    }

    /* for updating simple fields */
    public void updateObject(T oldObject, T newObject) {
        List<Bson> updates = new ArrayList<>();
        try {
            for (Field property : oldObject.getClass().getDeclaredFields()) {
                property.setAccessible(true);
                if ((property.get(oldObject) != null && property.get(newObject) != null &&
                        !property.get(oldObject).equals(property.get(newObject))) ||
                        (property.get(oldObject) == null && property.get(newObject) != null)) {
                    System.out.println("different " + property.getName());
                    updates.add(Updates.set(
                            property.getName(),
                            property.getName().equals("categories") ?
                                    ((Collection<Category>) property.get(newObject)).stream().map(Object::toString).collect(Collectors.toList()) :
                                    property.getType().cast(property.get(newObject))));
                }
            }
            db.getCollection(collectionName).updateOne(new IdFilter(oldObject.getId()).getQuery(), Updates.combine(updates));
        } catch (Exception e) {
            System.out.println("illegal access attempt");
        }
    }

    /* for adding to arrays */
    public <V> void updateObject(T object, V newElement, String arrayName) {
        db.getCollection(collectionName).updateOne(new IdFilter(object.getId()).getQuery(), Updates.push(arrayName, newElement));
    }

    public void deleteObjects(Filter filter) {
        db.getCollection(collectionName).deleteMany(filter.getQuery());
    }
}
