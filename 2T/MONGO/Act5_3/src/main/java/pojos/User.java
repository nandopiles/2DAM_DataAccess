package pojos;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * @author Nando
 */
public class User {
    @BsonId
    private final ObjectId id;
    private String name;
    private String email;
    private Address address;

    public User() {
        this.id = new ObjectId();
    }

    public User(String name, String email, Address address) {
        this.id = new ObjectId();
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
