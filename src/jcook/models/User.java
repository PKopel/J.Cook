package jcook.models;

import javafx.scene.image.Image;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class User implements Model {

    private ObjectId id;
    private String name;
    private List<ObjectId> uploaded;
    private List<ObjectId> rated;
    private byte[] password;
    private byte[] salt;
    private byte[] image;

    public User() {
    }

    public User(String name, List<ObjectId> uploaded, List<ObjectId> rated,
                byte[] salt, byte[] password, byte[] image) {
        this.name = name;
        this.uploaded = uploaded;
        this.rated = rated;
        this.password = password;
        this.image = image;
        this.salt = salt;
    }

    public User(User other) {
        this.id = other.id;
        this.name = other.name;
        this.uploaded = new LinkedList<>(other.uploaded);
        this.rated = new LinkedList<>(other.rated);
        this.password = other.password;
        this.salt = other.salt;
        this.image = other.image;
    }

    @BsonIgnore
    public static User offlineUser() throws IOException {
        return new User("offline", new LinkedList<>(), new LinkedList<>(), new byte[]{0}, new byte[]{0},
                User.class.getResourceAsStream("/images" + "/j_cook.jpeg").readAllBytes());
    }

    @BsonIgnore
    public Image getRenderedImage() {
        return new Image(new ByteArrayInputStream(getImage()));
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ObjectId> getUploaded() {
        return uploaded;
    }

    public void setUploaded(List<ObjectId> uploaded) {
        this.uploaded = uploaded;
    }

    public List<ObjectId> getRated() {
        return rated;
    }

    public void setRated(List<ObjectId> rated) {
        this.rated = rated;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
