package nz.co.ninjastudios.flixfinder;

import java.util.ArrayList;

/**
 * Created by james on 21/08/2017.
 */

public class TVShow extends Show {

    private int[] episode_run_time;
    private String first_air_date;
    private boolean in_production;
    private String[] languages;
    private String last_air_date;
    private String name;
    private int number_of_episodes;
    private int number_of_seasons;
    private Season[] seasons;

    public TVShow(){

    }

    public int[] getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(int[] episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
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

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
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

    public Season[] getSeasons() {
        return seasons;
    }

    public void setSeasons(Season[] seasons) {
        this.seasons = seasons;
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


