package pojos;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author Nando
 */
public class Article {
    @BsonId
    private final ObjectId id;
    private String name;
    private double price;
    private List<String> categories;
    private List<Comment> comments;

    public Article() {
        this.id = new ObjectId();
        this.comments = null;
    }

    public Article(String name, double price, List<String> categories) {
        this.id = new ObjectId();
        this.name = name;
        this.price = price;
        this.categories = categories;
        this.comments = null;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", categories=" + categories +
                ", comments=" + comments +
                '}';
    }
}
