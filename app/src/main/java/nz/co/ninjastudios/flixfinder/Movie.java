package nz.co.ninjastudios.flixfinder;

/**
 * Created by james on 21/08/2017.
 */

public class Movie extends Show {

    private boolean adult;
    private int budget;
    private int revenue;
    private int runtime;

    public Movie(){

    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Genre[] getGenres() {
        return super.getGenres();
    }

    public void setGenres(Genre[] genres) {
        super.setGenres(genres);
    }

    public int getId() {
        return super.getId();
    }

    public void setId(int id) {
        super.setId(id);
    }

    public String getOverview() {
        return super.getOverview();
    }

    public void setOverview(String overview) {
        super.setOverview(overview);
    }

    public String getPoster_path() {
        return super.getPoster_path();
    }

    public void setPoster_path(String poster_path) {
        super.setPoster_path(poster_path);
    }

    public String getRelease_date() {
        return super.getRelease_date();
    }

    public void setRelease_date(String release_date) {
        super.setRelease_date(release_date);
    }

    public int getYear(){
        return super.getYear();
    }

    public void setYear(int year){
        super.setYear(year);
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return super.getTitle();
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }

    public double getVote_average() {
        return super.getVote_average();
    }

    public void setVote_average(double vote_average) {
        super.setVote_average(vote_average);
    }

    public int getVote_count() {
        return super.getVote_count();
    }

    public void setVote_count(int vote_count) {
        super.setVote_count(vote_count);
    }
}
