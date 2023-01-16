import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.*;

public class Queries {


    static MongoClient client = new MongoClient();
    static MongoDatabase db = client.getDatabase("mflix");
    static MongoCollection<Document> movies = db.getCollection("movies");
    static Document projectDoc = new Document()
            .append("year", 1)
            .append("title", 1)
            .append("directors", 1)
            .append("_id", 0);

    /**
     * Get the data for the movie “Jurassic World”
     */
    public static void query1() {
        Bson filterDoc = eq("title", "Jurassic World");
        FindIterable<Document> findRes = movies.find(filterDoc).projection(projectDoc);

        System.out.println("Query 1");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    /**
     * Get the movies of the year 2016
     */
    public static void query2() {
        Bson filterDoc = eq("year", 2016);
        FindIterable<Document> findRes = movies.find(filterDoc).projection(projectDoc);

        System.out.println("Query 2");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    /**
     * Get the movies released between 2015 and 2016, both inclusive
     */
    public static void query3() {
        Bson filterDoc = and(gte("year", 2015), lte("year", 2016));
        FindIterable<Document> findRes = movies.find(filterDoc).limit(5).projection(projectDoc);

        System.out.println("Query 3");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    /**
     * Get the movies whose releasing year is a string ($type)
     */
    public static void query4() {
        Bson filterDoc = type("year", "string");
        FindIterable<Document> findRes = movies.find(filterDoc).limit(5).projection(projectDoc);

        System.out.println("Query 4");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    /**
     * Get the movies that have not defined a plot ($exists)
     */
    public static void query5() {

        Bson filterDoc = exists("plot", false);
        FindIterable<Document> findRes = movies.find(filterDoc).limit(5).projection(projectDoc);

        System.out.println("Query 5");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    /**
     * Get the movies filmed in Spain
     */
    public static void query6() {
        Document projectDoc6 = new Document()
                .append("year", 1)
                .append("title", 1)
                .append("countries", 1)
                .append("_id", 0);

        Bson filterDoc = eq("countries", "Spain");
        FindIterable<Document> findRes = movies.find(filterDoc).limit(5).projection(projectDoc6);

        System.out.println("Query 6");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("========================================");
    }

    /**
     * Get the movies with only one person in the cast and of the genre “Biography”
     */
    public static void query7() {
        Document projectDoc7 = new Document()
                .append("cast", 1)
                .append("title", 1)
                .append("genres", 1)
                .append("_id", 0);
        Bson filterDoc = and(size("cast", 1), eq("genres", "Biography"));
        FindIterable<Document> findRes = movies.find(filterDoc).limit(5).projection(projectDoc7);

        System.out.println("Query 7");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("========================================");
    }

    /**
     * Get the movies that have Spanish and English languages ($all)
     */
    public static void query8() {
        Document projectDoc8 = new Document()
                .append("title", 1)
                .append("languages", 1)
                .append("_id", 0);
        Bson filterDoc = all("languages", Arrays.asList("Spanish", "English"));
        FindIterable<Document> findRes = movies.find(filterDoc).limit(5).projection(projectDoc8);

        System.out.println("Query 8");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("========================================");
    }

    /**
     * Get the movies directed by Spielberg ($regex)
     */
    public static void query9() {
        Bson filterDoc = regex("directors", "spielberg", "i"); //insensible a mayus y minus
        FindIterable<Document> findRes = movies.find(filterDoc).limit(5).projection(projectDoc);

        System.out.println("Query 9");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("========================================");
    }

    /**
     * Get the movies directed by Spielberg OR Kubrick
     */
    public static void query10() {
        Bson filterDoc = or(regex("directors", "spielberg", "i"), regex("directors", "kubrick", "i"));
        FindIterable<Document> findRes = movies.find(filterDoc).limit(10).projection(projectDoc);

        System.out.println("Query 10");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("========================================");
    }

    /**
     * Get the movies that won more than 7 awards and have at least 9 points in IMDB
     */
    public static void query11() {
        Bson filterDoc = and(gt("awards.wins", 7), gte("imdb.rating", 9));
        FindIterable<Document> findRes = movies.find(filterDoc).limit(5).projection(projectDoc);

        System.out.println("Query 11");
        for (Document doc :
                findRes) {
            System.out.println(doc.toJson());
        }
        System.out.println("==================================");
    }

    public static void main(String[] args) {
        query1();
        query2();
        query3();
        query4();
        query5();
        query6();
        query7();
        query8();
        query9();
        query10();
        query11();

        client.close();
    }
}
