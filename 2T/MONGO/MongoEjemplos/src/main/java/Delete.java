import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

public class Delete {
    static MongoClient client = new MongoClient();
    static MongoDatabase db = client.getDatabase("mflix");
    static MongoCollection<Document> movies = db.getCollection("movies");
    static Document projectDoc = new Document()
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1)
            .append("_id", 0);

    public static void delete1() {
        Bson filterDoc = exists("year", false);
        movies.deleteMany(filterDoc);
        FindIterable<Document> findRes = movies.find(filterDoc).projection(projectDoc);

        System.out.println("Delete 1");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    public static void delete2() {
        db = client.getDatabase("new_mflix");
        movies = db.getCollection("movies");
        Bson filterDoc = eq("genres", "Fantasy");
        movies.deleteOne(filterDoc);
        FindIterable<Document> findRes = movies.find(filterDoc).projection(projectDoc);

        System.out.println("Delete 2");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    public static void delete3() {
        db = client.getDatabase("new_mflix");
        movies = db.getCollection("movies");
        Bson filterDoc = eq("rated", "+18");
        movies.deleteMany(filterDoc);
        FindIterable<Document> findRes = movies.find(filterDoc).projection(projectDoc);

        System.out.println("Delete 3");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    public static void delete4() {
        db = client.getDatabase("new_mflix");
        movies = db.getCollection("movies");

        System.out.println("Delete 4");
        movies.drop();
        System.out.println("[+] Collection \"movies\" dropped\n");

        System.out.println("==================================");
    }

    public static void main(String[] args) {
        delete1();
        delete2();
        delete3();
        delete4();
    }
}