import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class Update {
    static MongoClient client = new MongoClient();
    static MongoDatabase db = client.getDatabase("mflix");
    static MongoCollection<Document> movies = db.getCollection("movies");
    static Document projectDoc = new Document()
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1)
            .append("_id", 0);

    /**
     * For all the movies titled Titanic, add a field called mustsee2 with true value and add Spain to the countries
     * array.
     */
    public static void update1() {
        Bson filterDoc = eq("title", "Titanic");
        Bson updateDoc = and(
                set("year", true),
                push("countries", "Spain")
        );
        movies.updateMany(filterDoc, updateDoc);
        FindIterable<Document> findRes = movies.find(filterDoc).projection(projectDoc);

        System.out.println("Update 1");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    /**
     * For all the movies containing “Rings” in the title and with the num_mflix_comments field, add 50 to
     * num_mflix_comments.
     */
    public static void update2() {
        Bson filterDoc = and(
                regex("title", "Rings"),
                exists("num_mflix_comments", true)
        );
        Bson updateDoc = inc("num_mflix_comments", 50);
        movies.updateMany(filterDoc, updateDoc);
        FindIterable<Document> findRes = movies.find(filterDoc).projection(projectDoc);

        System.out.println("Update 2");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    public static void update3() {
        Bson filterDoc = and(
                regex("title", "Matrix"),
                eq("year", 2003)
        );
        Bson updateDoc = and(
                unset("awards"),
                rename("plot", "shortplot")
        );
        movies.updateOne(filterDoc, updateDoc);
        FindIterable<Document> findRes = movies.find(filterDoc).projection(projectDoc);

        System.out.println("Update 3");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    public static void main(String[] args) {
        update1();
        update2();
        update3();
    }
}
