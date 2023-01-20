import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    static MongoClient client = new MongoClient();
    static MongoDatabase db = client.getDatabase("new_social-network");
    static MongoCollection<Document> users = db.getCollection("users");

    public static void separator() {
        System.out.println("=====================================");
    }

    public static void insert2Users() {
        Document user1 = new Document()
                .append("_id", 5L)
                .append("name", "Juan")
                .append("surname", "García Castellano")
                .append("age", 23)
                .append("gender", "male")
                .append("registration_date", new Date());

        Document user2 = new Document()
                .append("_id", 6L)
                .append("name", "Beatriz")
                .append("surname", "Perez  Solaz")
                .append("age", 27)
                .append("gender", "female")
                .append("registration_date", new Date());

        users.insertMany(Arrays.asList(user1, user2));
    }

    public static void retrieveUsersCollection() {
        FindIterable<Document> usersCollection = users.find();

        System.out.println("Users Collection:");
        for (Document user :
                usersCollection) {
            System.out.println(user.toJson());
        }
        separator();
    }

    public static void insert() {
        Document newUser = new Document()
                .append("_id", 7L)
                .append("name", "Jorge")
                .append("surname", "López Sevilla")
                .append("gender", "male")
                .append("registration_date", new Date())
                .append("groups", Arrays.asList("basketball", "kitchen", "historical novel"));

        users.insertOne(newUser);

        Bson filterDoc = eq("_id", 7L);
        FindIterable<Document> findRes = users.find(filterDoc);
        for (Document user :
                findRes) {
            System.out.println(user.toJson());
        }
        separator();
    }

    public static void deleteUser() {
        users.deleteOne(eq("_id", 5));
        retrieveUsersCollection();
    }

    public static void updatePushingGroups() {

    }

    public static void main(String[] args) {
        users.drop();

        insert2Users();
        retrieveUsersCollection();
        insert();
        deleteUser();
    }
}
