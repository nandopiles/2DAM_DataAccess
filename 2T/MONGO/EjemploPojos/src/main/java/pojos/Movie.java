package pojos;

import java.util.List;

public class Movie {
    private String title;
    private int year;
    private List<String> directors;
    private List<String> genres;
    private Awards awards;

    public Movie() {
    }

    public Movie(String title, int year,
                 List<String> directors,
                 List<String> genres, Awards awards) {
        this.title = title;
        this.year = year;
        this.directors = directors;
        this.genres = genres;
        this.awards = awards;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Awards getAwards() {
        return awards;
    }

    public void setAwards(Awards awards) {
        this.awards = awards;
    }

    @Override
    public String toString() {
        return "pojos.Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", directors=" + directors +
                ", genres=" + genres +
                ", awards=" + awards +
                '}';
    }
}
