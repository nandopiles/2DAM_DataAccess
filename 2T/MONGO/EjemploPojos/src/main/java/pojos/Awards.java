package pojos;

public class Awards {
    private int wins;
    private int nominations;
    private String text;

    public Awards() {
    }

    public Awards(int wins, int nominations, String text) {
        this.wins = wins;
        this.nominations = nominations;
        this.text = text;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getNominations() {
        return nominations;
    }

    public void setNominations(int nominations) {
        this.nominations = nominations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "pojos.Awards{" +
                "wins=" + wins +
                ", nominations=" + nominations +
                ", text='" + text + '\'' +
                '}';
    }
}
