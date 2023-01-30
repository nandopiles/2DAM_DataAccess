package exceptions;

public class ScoreOutOfBoundException extends Exception {
    public ScoreOutOfBoundException(String errorMessage) {
        super(errorMessage);
    }
}
