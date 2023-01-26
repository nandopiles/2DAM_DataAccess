package pojos;

import org.bson.types.ObjectId;

/**
 * @author Nando
 */
public class Comment {
    private int score;
    private ObjectId userId;
    private String text;

    public Comment() {
    }

    public Comment(int score, ObjectId userId, String text) {
        this.score = score;
        this.userId = userId;
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "score=" + score +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                '}';
    }
}
