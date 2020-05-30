package jcook.providers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jcook.filters.Filter;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Collection;
import java.util.LinkedList;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class AbstractProvider<T> {
    protected final MongoDatabase db;
    private final String collectionName;
    private final Class<T> clazz;

    protected AbstractProvider(String databaseName, String collectionName, Class<T> clazz) {
        this.collectionName = collectionName;
        this.clazz = clazz;
        ConnectionString connection = new ConnectionString("mongodb://127.0.0.1");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connection)
                .codecRegistry(pojoCodecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        db = mongoClient.getDatabase(databaseName);
    }

    public Collection<T> getObjects(Filter filter) {
        return db.getCollection(collectionName, clazz)
                .find(filter.getQuery())
                .into(new LinkedList<>());
    }

    public void addObject(T object) {
        db.getCollection(collectionName, clazz)
                .insertOne(object);
    }
}
