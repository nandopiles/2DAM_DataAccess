import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.Date;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class Main {
    static MongoClient client = new MongoClient();
    static MongoDatabase db = client.getDatabase("new_social-network");
    static MongoCollection<Document> users = db.getCollection("users");
    static MongoCollection<Document> company;

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

    public static void showOneUser(Bson filterDoc) {
        FindIterable<Document> findRes = users.find(filterDoc);
        for (Document user :
                findRes) {
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
        showOneUser(filterDoc);
    }

    public static void deleteUser() {
        users.deleteOne(eq("_id", 5L));
        retrieveUsersCollection();
    }

    public static void updatePushingGroups() {
        Bson filterDoc = eq("_id", 6L);
        Bson updateDoc = pushEach("groups", Arrays.asList("historical novel", "dance"));
        users.updateOne(filterDoc, updateDoc);
        showOneUser(filterDoc);
    }

    public static void showOneCompany(Bson filterDoc) {
        company = db.getCollection("company");
        FindIterable<Document> findRes = company.find(filterDoc);
        for (Document com :
                findRes) {
            System.out.println(com.toJson());
        }
        separator();
    }

    public static void createCompany() {
        db.createCollection("company");
        company = db.getCollection("company");
        Document newCompany = new Document()
                .append("_id", 10L)
                .append("name", "Gardening Gardenia");
        company.insertOne(newCompany);
        showOneCompany(eq("name", "Gardening Gardenia"));
    }

    public static void updateCompanyAddress() {
        company = db.getCollection("company");
        company.updateOne(
                eq("_id", 10L),
                and(
                        set("address", new Document()
                                .append("street", "Palmeras")
                                .append("number", 8)
                                .append("town", "Torrente")),
                        set("sector", "services"),
                        set("web", "http://www.gardeninggardenia.com")
                ));
        showOneCompany(eq("name", "Gardening Gardenia"));
    }

    public static void updateFollowersIncrement() {
        company = db.getCollection("company");
        System.out.println("!------------------------------!");
        company.updateOne(
                eq("name", "Gardening Gardenia"),
                set("followers", 5)
        );
        showOneCompany(eq("name", "Gardening Gardenia"));
        company.updateOne(
                eq("name", "Gardening Gardenia"),
                inc("followers", 2)
        );
        showOneCompany(eq("name", "Gardening Gardenia"));
        company.updateOne(
                eq("name", "Gardening Gardenia"),
                inc("followers", -1)
        );
        showOneCompany(eq("name", "Gardening Gardenia"));
        System.out.println("!------------------------------!");
    }

    public static void updateAddressPostal() {
        company = db.getCollection("company");
        company.updateOne(
                eq("name", "Gardening Gardenia"),
                set("address.postal", 46009)
        );
        showOneCompany(eq("name", "Gardening Gardenia"));
    }

    public static void removeSector() {
        company = db.getCollection("company");
        company.updateOne(
                eq("name", "Gardening Gardenia"),
                unset("sector"));
        showOneCompany(eq("name", "Gardening Gardenia"));
    }

    public static void pushGroup() {
        users.updateOne(
                eq("name", "Beatriz"),
                push("groups", "theatre")
        );
        showOneUser(eq("name", "Beatriz"));
    }

    public static void pullGroup() {
        users.updateOne(
                eq("_id", 6L),
                pull("groups", "Dance")
        );
        showOneUser(eq("_id", 6L));
    }

    public static void writeComments() {
        users.updateOne(
                and(
                        eq("name", "Jorge"),
                        eq("surname", "López Sevilla")
                ),
                and(
                        set("comments", Arrays.asList(new Document()
                                .append("title", "Comment 1")
                                .append("text", "Hola pixa aquí Joaquín")
                                .append("group", "historical novel")
                                .append("date", new Date())
                        )),
                        set("total_comments", 1)
                )
        );
        showOneUser(eq("name", "Jorge"));

        users.updateOne(
                and(
                        eq("name", "Jorge"),
                        eq("surname", "López Sevilla")
                ),
                and(
                        push("comments", Arrays.asList(new Document()
                                .append("title", "Comment 2")
                                .append("text", "AAAA")
                                .append("group", "Basketball")
                                .append("date", new Date())
                        )),
                        inc("total_comments", 1)
                )
        );
        showOneUser(eq("name", "Jorge"));
    }

    public static void doQueries() {
        Document projectDoc = new Document()
                .append("_id", 1)
                .append("name", 1)
                .append("surname", 1)
                .append("age", 1);

        System.out.println("Query 1");
        FindIterable<Document> query1 = users.find(
                and(
                        eq("groups", "historical novel"),
                        gte("age", 25))
        ).projection(projectDoc);
        for (Document d :
                query1) {
            System.out.println(d.toJson());
        }

        Document projectDoc2 = new Document()
                .append("_id", 0)
                .append("name", 1)
                .append("surname", 1)
                .append("groups", 1);
        System.out.println("Query 2");
        FindIterable<Document> query2 = users.find(
                exists("groups.1", true)
        ).projection(projectDoc2);
        for (Document d :
                query2) {
            System.out.println(d.toJson());
        }

        System.out.println("Query 3");
        FindIterable<Document> query3 = users.find(
                all("groups", Arrays.asList("historical novel", "theatre"))
        ).projection(projectDoc2);
        for (Document d :
                query3) {
            System.out.println(d.toJson());
        }

        Document projectDoc3 = new Document()
                .append("_id", 0)
                .append("name", 1)
                .append("surname", 1);
        System.out.println("Query 4");
        FindIterable<Document> query4 = users.find(
                exists("comments", true)
        ).projection(projectDoc3);
        for (Document d :
                query4) {
            System.out.println(d.toJson());
        }

        Document projectDoc4 = new Document()
                .append("_id", 0)
                .append("name", 1);
        System.out.println("Query 5");
        FindIterable<Document> query5 = users.find(
                and(
                        eq("address.town", "Torrente"),
                        size("followers", 0)
                )).projection(projectDoc4);
        for (Document d :
                query5) {
            System.out.println(d.toJson());
        }

        System.out.println("Query 6");
        FindIterable<Document> query6 = users.find(
                and(
                        eq("address.town", "Torrente"),
                        gte("followers", 5)
                )).projection(projectDoc4);
        for (Document d :
                query6) {
            System.out.println(d.toJson());
        }
    }

    public static void main(String[] args) {
        db.drop();

        insert2Users();
        retrieveUsersCollection();
        insert();
        deleteUser();
        updatePushingGroups();
        createCompany();
        updateCompanyAddress();
        updateFollowersIncrement();
        updateAddressPostal();
        removeSector();
        pushGroup();
        pullGroup();
        writeComments();
        doQueries();
    }
}
