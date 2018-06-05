package nz.co.ninjastudios.flixfinder;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by james on 21/08/2017.
 */

public abstract class Show implements Serializable {

    private int id;
    private Genre[] genres;
    private String overview;
    private String poster_path;
    private double vote_average;
    private int vote_count;
    private String status;
    private String title;
    private String release_date;
    private int year;
    private ArrayList<Country> countries;
    private boolean isOnNetflix;

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

    protected String getStatus() {
        return status;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    protected String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected String getRelease_date() {
        return release_date;
    }

    protected void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    protected int getYear() {
        return year;
    }

    protected void setYear(int year) {
        this.year = year;
    }

    protected ArrayList<Country> getCountries() {
        return countries;
    }

    protected void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    protected boolean isOnNetflix() {
        return isOnNetflix;
    }

    protected void setOnNetflix(boolean onNetflix) {
        isOnNetflix = onNetflix;
    }
}
