package api;

import alerts.Colors;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import pojos.Address;
import pojos.Article;
import pojos.Comment;
import pojos.User;

import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author Nando
 */
public class DataAPI implements Colors {
    private static MongoClient client;
    private static MongoDatabase db;

    /**
     * Initializes the MongoDB client and the DB, able to manage POJOs
     */
    public static void init() {
        String dbName = "act5_3";
        client = new MongoClient();

        CodecRegistry pojoCodecRegistry =
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        db = client.getDatabase(dbName)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.printf("%s[+] Connected to the DB => %s%s\n", ANSI_LIGHT_BROWN, dbName, ANSI_RESET);
    }

    /**
     * Closes de Mongoclient
     */
    public static void close() {
        client.close();
        System.out.printf("%s[+] Client has been closed %s\n", ANSI_LIGHT_BROWN, ANSI_RESET);
    }

    /**
     * Inserts an article to the DB
     */
    public static void insertArticle(Article article) {
        MongoCollection<Article> articles = db.getCollection("articles", Article.class);
        articles.insertOne(article);

        System.out.printf("%s[+] Article added => %s%s\n", ANSI_GREEN, article.getName(), ANSI_RESET);
    }

    /**
     * Inserts a user to the DB
     */
    public static void insertUser(User user) {
        MongoCollection<User> users = db.getCollection("users", User.class);
        users.insertOne(user);

        System.out.printf("%s[+] User added => %s%s\n", ANSI_GREEN, user.getName(), ANSI_RESET);
    }

    /**
     * Finds an article by its id
     */
    public static Article findArticle(ObjectId id) {
        MongoCollection<Article> articles = db.getCollection("articles", Article.class);

        return articles.find(eq("_id", id)).first();
    }

    /**
     * Returns all the articles that are of the category cat
     */
    public static FindIterable<Article> findArticleByCategory(String category) {
        MongoCollection<Article> articles = db.getCollection("articles", Article.class);

        return articles.find(eq("categories", category));
    }

    /**
     * Returns all the articles that contains "str" in its name
     */
    public static FindIterable<Article> findArticleByName(String str) {
        MongoCollection<Article> articles = db.getCollection("articles", Article.class);

        return articles.find(regex("name", Pattern.compile(str)));
    }

    /**
     * Returns all the articles whose price is in the rank [low, high], both inclusive
     */
    public static FindIterable<Article> findArticleInPriceRank(double low, double high) {
        MongoCollection<Article> articles = db.getCollection("articles", Article.class);

        return articles.find(
                and(
                        lte("price", high),
                        gte("price", low)
                ));
    }

    /**
     * Finds a user with its id
     */
    public static User findUser(ObjectId id) {
        MongoCollection<User> users = db.getCollection("users", User.class);

        return users.find(eq("_id", id)).first();
    }

    /**
     * Finds all the users of the country specified by argument
     */
    public static FindIterable<User> findUserByCountry(String country) {
        MongoCollection<User> users = db.getCollection("users", User.class);

        return users.find(eq("address.country", country));
    }

    /**
     * Receives a FindIterable<Article> object and returns it ordered by price ascending or descending as specified as
     * argument.
     */
    public static FindIterable<Article> orderByPrice(FindIterable<Article> articles, boolean asc) {
        return asc ? articles.sort(ascending("price")) : articles.sort(descending("price"));
    }

    /**
     * Updates the address of the user specified by parameter
     */
    public static void updateAddress(User user, Address address) {
        MongoCollection<User> users = db.getCollection("users", User.class);
        users.updateOne(
                eq("_id", user.getId()),
                set("address", address));
        System.out.println(ANSI_GREEN + "\n[+] Updated Address of user => " + user.getName() + ANSI_RESET);
        System.out.println(ANSI_LIGHT_BROWN + "[*] Update => " + findUser(user.getId()) + ANSI_RESET);
    }

    public static void updateEmail(User us, String email) {

    }

    public static void addComment(Article art, Comment newCom) {

    }

    public static void deleteArticle(Article art) {

    }

    public static void deleteUser(User us) {

    }
}
