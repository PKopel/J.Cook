package jcook.providers;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import jcook.filters.Filter;
import jcook.filters.NameFilter;
import jcook.models.Category;
import jcook.models.Ingredient;
import jcook.models.Model;
import jcook.models.Recipe;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

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
    public void updateObject(T oldObject,T newObject){
        BasicDBObject query = new BasicDBObject();
        query.put("_id", object.getId());
        try {
            Field property = clazz.getDeclaredField(propertyName);
            property.setAccessible(true);
            db.getCollection(collectionName)
                    .updateOne(
                            query,Updates.set(
                                    propertyName,
                                    property.getType().cast(property.get(object)
                                    )
                            )
                    );
        } catch (Exception e) {
            System.out.println("illegal property update attempt");
        }
    }

    /* for updating arrays */
    public <V> void updateObject(T object, V newElement, String arrayName){
        BasicDBObject query = new BasicDBObject();
        try {
            query.put("_id", object.getId());
            db.getCollection(collectionName).updateOne(query,Updates.push(arrayName, newElement));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteObjects(Filter filter){
        db.getCollection(collectionName).deleteMany(filter.getQuery());
    }
}
