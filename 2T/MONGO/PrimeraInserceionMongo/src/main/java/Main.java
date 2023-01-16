import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;


import java.util.Arrays;

import static com.mongodb.client.model.Filters.exists;

public class Main {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("mflix");

        //db.createCollection("movies");
        MongoCollection<Document> movies = db.getCollection("movies");

        Document newMovie1 = new Document()
                .append("title", "Lord of Rings")
                .append("genres", Arrays.asList("Adventure", "Fantasy"))
                .append("type", "Movie")
                .append("rated", "+13")
                .append("year", 2001)
                .append("director", "Peter Jackson")
                .append("cast", Arrays.asList(
                        "Elijah Wood",
                        "Ian McKellen",
                        "Liv Tyler",
                        "Viggo Mortensen"
                ));

        Document newMovie2 = new Document()
                .append("title", "Matrix")
                .append("genres", Arrays.asList("Sci-fi", "Cyberpunk"))
                .append("type", "Movie")
                .append("rated", "+18")
                .append("year", 1999)
                .append("director", "The Wachowskis")
                .append("cast", Arrays.asList(
                        "Keanu Reeves",
                        "Laurence Fishburne",
                        "Carrie-Anne Moss"
                ));

        Document newMovie3 = new Document()
                .append("title", "Game of thrones")
                .append("genres", Arrays.asList("Fantasy", "Intrigue"))
                .append("type", "Series")
                .append("rated", "+18")
                .append("year", 2011)
                .append("cast", Arrays.asList(
                        "Peter Dinklage",
                        "Lena Headey",
                        "Kit Harington",
                        "Emilia Clarke"
                ));

        Document newMovie4 = new Document()
                .append("title", "Data Access: the Movie")
                .append("genres", Arrays.asList("Drama", "Horror"))
                .append("type", "Movie")
                .append("rated", "+18")
                .append("year", 2021)
                .append("director", "Xavier Ibáñez");

        /*movies.insertOne(newMovie1);
        System.out.printf("[+] Document \"%s\"\n", newMovie1.getString("title"));
        movies.insertMany(Arrays.asList(newMovie2, newMovie3, newMovie4));
        System.out.printf("[+] Document \"%s\"\n", newMovie2.getString("title"));
        System.out.printf("[+] Document \"%s\"\n", newMovie3.getString("title"));
        System.out.printf("[+] Document \"%s\"\n", newMovie4.getString("title"));*/

        Document sortDoc = new Document("year", 1);
        FindIterable<Document> findRes = movies.find().sort(sortDoc);
        System.out.println("Oldest movie: " + findRes.first().toString());

        Document proyectDoc = new Document()
                .append("year", 1)
                .append("title", 1)
                .append("_id", 0);

        findRes = findRes.skip(1).limit(5).projection(proyectDoc);
        for (Document doc :
                findRes) {
            System.out.println(doc);
        }

        Bson filterDoc = exists("year", false);
        findRes = movies.find(filterDoc).limit(5).projection(proyectDoc);
        System.out.println("\n");
        for (Document doc :
                findRes) {
            System.out.println(doc.getString("title"));
        }
        client.close();
    }
}