package nz.co.ninjastudios.flixfinder;

import java.util.ArrayList;

/**
 * Created by james on 21/08/2017.
 */

public class Show {
    private String title;
    private String description;
    private String recommendedCountry;
    private ArrayList<String> tags;
    private int year;
    private ArrayList<Country> countries;
    private int rating;

    public Show(){

    }

    protected String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected String getRecommendedCountry() {
        return recommendedCountry;
    }

    protected void setRecommendedCountry(String recommendedCountry) {
        this.recommendedCountry = recommendedCountry;
    }

    protected ArrayList<String> getTags() {
        return tags;
    }

    protected void setTags(ArrayList<String> tags) {
        this.tags = tags;
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

    protected int getRating() {
        return rating;
    }

    protected void setRating(int rating) {
        this.rating = rating;
    }
}
