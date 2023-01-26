import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import pojos.Awards;
import pojos.Movie;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        //traductor de MongoDB a POJOS
        CodecRegistry pojoCodecRegistry =
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoDatabase db = client.getDatabase("mflix");
        //esta BD va a utilizar este traductor
        db = db.withCodecRegistry(pojoCodecRegistry);

        MongoCollection<Movie> movies = db.getCollection("movies", Movie.class);
        FindIterable<Movie> res = movies.find().limit(5);
        for (Movie elem :
                res) {
            System.out.println(elem);
        }
        //____________________________
        Movie newMov = new Movie("Fake Movie 1",
                2022,
                Arrays.asList("Romantic", "western"),
                Arrays.asList("John Smith"),
                new Awards(1, 2, "fake nominaiton"));

        System.out.println("[*] Inserting Movie: " + newMov);
        movies.insertOne(newMov);

        System.out.println("[*] Retrieving new movie from database...");
        FindIterable<Movie> findResult = movies.find(eq("year", 2022));
        System.out.println("[+] First Movie of 2022: "
                + findResult.first());

        System.out.println("[*] Deleting a movie of 2022");
        movies.deleteOne(eq("year", 2022));
        System.out.println("[+] Deleted");

        client.close();
    }
}
