package nz.co.ninjastudios.flixfinder;

/**
 * Created by james on 21/08/2017.
 */

public class Show {

    private int id;
    private Genre[] genres;
    private String overview;
    private String poster_path;
    private double vote_average;
    private int vote_count;

    public Show(){

    }

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected Genre[] getGenres() {
        return genres;
    }

    protected void setGenres(Genre[] genres) {
        this.genres = genres;
    }

    protected String getOverview() {
        return overview;
    }

    protected void setOverview(String overview) {
        this.overview = overview;
    }

    protected String getPoster_path() {
        return poster_path;
    }

    protected void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    protected double getVote_average() {
        return vote_average;
    }

    protected void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    protected int getVote_count() {
        return vote_count;
    }

    protected void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
