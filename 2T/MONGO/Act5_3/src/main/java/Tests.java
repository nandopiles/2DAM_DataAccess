import alerts.Colors;
import api.DataAPI;
import com.mongodb.client.FindIterable;
import pojos.Address;
import pojos.Article;
import pojos.Comment;
import pojos.User;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nando
 */
public class Tests implements Colors {
    public static void separator() {
        System.out.println(ANSI_BLUE + "=============================================" + ANSI_RESET);
    }

    public static void main(String[] args) {
        DataAPI.init();

        Article article1 = new Article(
                "Test11",
                11,
                Arrays.asList("Sports", "Casual"));
        Article article2 = new Article(
                "Test2",
                3,
                Arrays.asList("Fashion", "Sports"));
        Article article3 = new Article(
                "Test11",
                24,
                List.of("News"));
//insert Articles
        DataAPI.insertArticle(article1);
        DataAPI.insertArticle(article2);
        DataAPI.insertArticle(article3);
//find an Article by its id
        Article articleExemple = DataAPI.findArticle(article1.getId());
        System.out.println("\nFind Id: " + article1.getId() + "\n"
                + ANSI_YELLOW + articleExemple + ANSI_RESET);
        separator();
//find all the Articles with the category
        String category = "Sports";
        FindIterable<Article> articlesCategories = DataAPI.findArticleByCategory(category);
        System.out.println("\nFind all Articles of \"" + category + "\"");
        for (Article article :
                articlesCategories) {
            System.out.println(ANSI_PURPLE + article + ANSI_RESET);
            separator();
        }
//find all the Articles with the name
        String name = "Test1";
        FindIterable<Article> articlesNames = DataAPI.findArticleByName(name);
        System.out.println("\nFind all Articles named \"" + name + "\":");
        for (Article article :
                articlesNames) {
            System.out.println(ANSI_PURPLE + article + ANSI_RESET);
            separator();
        }
//find the articles with a rank price
        double low = 10, high = 25;
        FindIterable<Article> articlesRank = DataAPI.findArticleInPriceRank(low, high);
        System.out.println("\nFind all Articles ranked " + low + " to " + high + ":");
        for (Article article :
                articlesRank) {
            System.out.println(ANSI_PURPLE + article + ANSI_RESET);
            separator();
        }
//sort a list by its price
        System.out.println("\nSort the list:");
        FindIterable<Article> articlesSorted = DataAPI.orderByPrice(articlesRank, true);
        for (Article article :
                articlesSorted) {
            System.out.println(ANSI_PURPLE + article + ANSI_RESET);
            separator();
        }

//insert users
        User user1 = new User(
                "Nando",
                "nandopiles@gmail.com",
                new Address(
                        "C/siu",
                        8,
                        "CityTest",
                        "CountryTest"
                ));
        User user2 = new User(
                "Primo Nando",
                "elprimo@gmail.com",
                new Address(
                        "C/siu",
                        1,
                        "CityTest",
                        "CountryTest"
                ));
        User user3 = new User(
                "Calo",
                "calololo@gmail.com",
                new Address(
                        "C/lentejas",
                        8,
                        "CitySasa",
                        "CountryTest"
                ));
        DataAPI.insertUser(user1);
        DataAPI.insertUser(user2);
        DataAPI.insertUser(user3);
//find an Article by its id
        User userExemple = DataAPI.findUser(user1.getId());
        System.out.println("\nFind Id User: " + user1.getId() + "\n"
                + ANSI_YELLOW + userExemple + ANSI_RESET);
        separator();
//finds all the users of the country specified
        String country = "CountryTest";
        System.out.println("\nUsers of the country " + country + ":");
        FindIterable<User> usersCountry = DataAPI.findUserByCountry(country);
        for (User user :
                usersCountry) {
            System.out.println(ANSI_PURPLE + user + ANSI_RESET);
            separator();
        }
//update the address
        Address newAddress = new Address(
                "C/macarrones",
                1,
                "CitySasa",
                "CountryTest"
        );
        DataAPI.updateAddress(user3, newAddress);
//update the email
        DataAPI.updateEmail(user3, "perdedor@gmail.com");
//add a comment
        Comment comment1 = new Comment(
                1,
                user1.getId(),
                "Test Comment 1"
        );
        DataAPI.addComment(article1, comment1);
//delete a user
        DataAPI.deleteUser(user3);
//delete an article
        DataAPI.deleteArticle(article3);

        DataAPI.close();
    }
}
