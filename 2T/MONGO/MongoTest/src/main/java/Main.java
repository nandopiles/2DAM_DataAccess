import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.BsonArray;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();

        MongoIterable<String> names = client.listDatabaseNames();
        String res = "Available Databases: ";
        for (String string : names) {
            res += string + "; ";
        }
        System.out.println(res);

        MongoDatabase db = client.getDatabase("mflix");

        System.out.println("Database Name: " + db.getName());
        MongoIterable<String> kk = db.listCollectionNames();
        for (String string : kk) {
            System.out.println(string);
        }
        MongoCollection<Document> movies = db.getCollection("movies");
        System.out.println("Nº documentos: " + movies.countDocuments());

        Document newMovie = new Document().append("title", "Lord of Rings").append("year", 2001);
        System.out.println("To String: " + newMovie.toString());
        System.out.println("Title: " + newMovie.getString("title"));
        String fieldName = "year";

        System.out.println(newMovie.containsKey(fieldName)
                ? "Contains " + fieldName + "field: " + newMovie.getInteger(fieldName)
                : "Does not contain " + fieldName + " field");

        client.close();
    }
}
