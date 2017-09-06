package nz.co.ninjastudios.flixfinder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.provider.DocumentFile;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private CircularProgressBar progressBar;
    private CircularProgressBar progressBarPages;
    private EditText editText;
    private ProgressDialog progressDialog;
    private String api_key = "5549383f29245415046c05c039ef2009";
    private Gson gson;
    private Context context;
    private TextView textViewGenre;
    private Spinner spinnerGenre;
    private Genre[] genres;
    private CheckBox checkBoxNetflix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();

        context = this;

        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenre);
        textViewGenre = (TextView) findViewById(R.id.textViewGenre);

        checkBoxNetflix = (CheckBox) findViewById(R.id.checkBoxNetflix);

        final SeekBar seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
        final TextView textViewRatingNum = (TextView) findViewById(R.id.textViewRatingNum);

        final EditText editTextReleaseYear = (EditText) findViewById(R.id.editTextReleaseYear);

        seekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewRatingNum.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final Spinner spinnerShowType = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.show_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowType.setAdapter(adapter);

        spinnerShowType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(!spinnerShowType.getItemAtPosition(position).toString().equals("--Select Show Type--")){
                    String showType = spinnerShowType.getItemAtPosition(position).toString();
                    new GetGenresTask(showType).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        progressBar = (CircularProgressBar) findViewById(R.id.progressBarSearch);
        progressBarPages = (CircularProgressBar) findViewById(R.id.progressBarPageScan);
        editText = (EditText) findViewById(R.id.editTextSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter a search term", Toast.LENGTH_SHORT).show();
                } else {
                    String searchTerm = editText.getText().toString();
                    String showType = spinnerShowType.getSelectedItem().toString();
                    Genre genre = (Genre) spinnerGenre.getItemAtPosition(spinnerGenre.getSelectedItemPosition());
                    int minRating = seekBarRating.getProgress();
                    int releaseYear = 0;

                    if(!editTextReleaseYear.getText().toString().isEmpty()){
                        releaseYear = Integer.parseInt(editTextReleaseYear.getText().toString());
                    }

                    new TheMovieDBSearchTask(searchTerm, showType, genre.getName(), minRating, releaseYear).execute();
                }

            }
        });
    }

    private class GetGenresTask extends AsyncTask<Void, Void, Void> {

        private String showType;

        public GetGenresTask(String showType){
            if(showType.equals("Movie")){
                this.showType = "movie";
            } else if(showType.equals("TV Show")){
                this.showType = "tv";
            }
        }

        @Override
        protected void onPreExecute(){
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Getting Genres...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Ion.with(context)
                    .load("https://api.themoviedb.org/3/genre/" + showType + "/list?api_key=" + api_key)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            textViewGenre.setVisibility(View.VISIBLE);
                            spinnerGenre.setVisibility(View.VISIBLE);

                            genres = gson.fromJson(result.getAsJsonArray("genres"), Genre[].class);
                            GenreSpinAdapter genreSpinAdapter = new GenreSpinAdapter(context, android.R.layout.simple_spinner_item, genres);
                            spinnerGenre.setAdapter(genreSpinAdapter);
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    private class TheMovieDBSearchTask extends AsyncTask<Void, Void, Void> {

        private String searchTerm;
        private String showType;
        private String genreFilter;
        private int minRating;
        private int releaseYear;
        private String url;
        private String releaseYearProperty;
        private ArrayList<Show> filterResults;

        public TheMovieDBSearchTask(String searchTerm, String showType, String genreFilter, int minRating, int releaseYear){
            this.searchTerm = searchTerm;
            this.genreFilter = genreFilter;
            this.minRating = minRating;
            this.releaseYear = releaseYear;
            this.filterResults = new ArrayList<>();

            this.url = "https://api.themoviedb.org/3/search/";

            if(showType.equals("Movie")){
                this.showType = "movie";
                this.releaseYearProperty = "primary_release_year";
            } else if(showType.equals("TV Show")){
                this.showType = "tv";
                this.releaseYearProperty = "first_air_date_year";
            }

            this.url += this.showType + "?api_key=" + api_key + "&query=" + searchTerm;

            if(releaseYear != 0){
                this.url += "&" + releaseYearProperty + "=" + this.releaseYear;
            }
        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Ion.with(getApplicationContext())
                    .load(this.url)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            JsonArray results = result.get("results").getAsJsonArray();
                            JsonObject[] resultsArray = gson.fromJson(results, JsonObject[].class);

                            for(JsonObject object : resultsArray){
                                String title = "";

                                if(showType.equals("movie")){
                                    title = object.get("title").getAsString();
                                } else {
                                    title = object.get("name").getAsString();
                                }

                                int[] genre_ids = gson.fromJson(object.getAsJsonArray("genre_ids"), int[].class);
                                boolean hasMatchingGenre = false;
                                double vote_average = object.get("vote_average").getAsDouble();

                                int releaseYearAPI = 0;

                                if(showType.equals("movie")){
                                    if(object.get("release_date").toString().contains("-")){
                                        releaseYearAPI = Integer.parseInt(object.get("release_date").getAsString().split("-")[0]);
                                    }
                                } else if(showType.equals("tv")){
                                    if(object.get("first_air_date").toString().contains("-")){
                                        releaseYearAPI = Integer.parseInt(object.get("first_air_date").getAsString().split("-")[0]);
                                    }
                                }

                                for(int genreId : genre_ids){
                                    // Get genre Name
                                    String genreName;

                                    for(Genre genre : genres){
                                        if(genre.getId() == genreId){
                                            genreName = genre.getName();

                                            if(genreName.equals(genreFilter)){
                                                hasMatchingGenre = true;
                                                break;
                                            }
                                        }
                                    }
                                }


                                if(title.contains(searchTerm) && hasMatchingGenre && vote_average >= minRating){
                                    if(releaseYear != 0){
                                        if(releaseYearAPI == releaseYear){
                                            Show show = null;

                                            if(showType.equals("movie")){
                                                show = new Movie();
                                            } else if(showType.equals("tv")){
                                                show = new TVShow();
                                            }

                                            show.setId(object.get("id").getAsInt());
                                            show.setTitle(title);
                                            show.setVote_average(vote_average);
                                            show.setYear(releaseYearAPI);
                                            show.setOnNetflix(false);


                                            filterResults.add(show);
                                        }
                                    } else {
                                        Show show = null;

                                        if(showType.equals("movie")){
                                            show = new Movie();
                                        } else if(showType.equals("tv")){
                                            show = new TVShow();
                                        }

                                        show.setId(object.get("id").getAsInt());
                                        show.setTitle(title);
                                        show.setVote_average(vote_average);
                                        show.setYear(releaseYearAPI);
                                        show.setOnNetflix(false);

                                        filterResults.add(show);
                                    }
                                }
                            }

                            if(filterResults.isEmpty()){
                                Toast.makeText(context, "No Results Found Matching Those Filters", Toast.LENGTH_LONG).show();
                            } else {
                                if(checkBoxNetflix.isChecked()){
                                    new FilterNetflixShowsTask(filterResults).execute();
                                } else {
                                    launchResultsActivity(filterResults);
                                }
                            }

                        }
                    });

            return null;

        }

        @Override
        protected void onPostExecute(Void result){

        }
    }

    private class FilterNetflixShowsTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<Show> showList;

        public FilterNetflixShowsTask(ArrayList<Show> showList){
            this.showList = showList;
        }

        @Override
        protected void onPreExecute(){
            progressBar.setProgress(0);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final ListIterator<Show> iterator = showList.listIterator();
            final int showListSize = showList.size();
            int count = 1;

            while(iterator.hasNext()){
                final int finalCount = count;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        float progress = (100/(float) showListSize) * (float) finalCount;
                        progressBar.setProgressWithAnimation(progress, 1000);
                    }
                });

                Show show = iterator.next();

                String urlEncodedTitle = "";

                if(show.getTitle().contains(" ")){
                    String tempTitle = show.getTitle();
                    urlEncodedTitle = tempTitle.replaceAll(" ", "-").toLowerCase();
                    tempTitle = null;
                } else {
                    urlEncodedTitle = show.getTitle().toLowerCase();
                }

                boolean endOfPages = false;
                boolean wasRedirected = false;
                String urlString = "http://flixsearch.io/search/" + urlEncodedTitle + "?page=1";

                // Check if url redirects (invalid show title), otherwise proceed with scanning each page
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setInstanceFollowRedirects(false);
                    urlConnection.connect();

                    int responseCode = urlConnection.getResponseCode();

                    if(responseCode == 301 || responseCode == 302 || responseCode == 303){
                        wasRedirected = true;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int page = 1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBarPages.setProgressWithAnimation((float) 0, 1000);
                    }
                });

                while(!endOfPages && !wasRedirected){
                    urlString = "http://flixsearch.io/search/" + urlEncodedTitle + "?page=" + page;
                    System.out.println("Page " + page);
                    Document document = null;

                    if(!wasRedirected){
                        try {
                            document = Jsoup.connect(urlString).get();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(document != null){
                            Elements elementNoResult = document.select("strong:contains(No results)");
                            Elements elementPagination = document.select("ul.pagination");
                            Elements paginationTabs = null;

                            if(elementPagination.size() > 0){
                                paginationTabs = elementPagination.get(0).children();
                            }

                            int totalPages = 1;

                            if(paginationTabs != null){
                                totalPages = Integer.parseInt(paginationTabs.get(paginationTabs.size() - 2).text());
                            }

                            endOfPages = elementNoResult.size() > 0 && elementPagination.size() == 0;

                            if(!endOfPages){
                                final int finalPage = page;
                                final int finalTotalPages = totalPages;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBarPages.setProgressWithAnimation((100/(float) finalTotalPages) * (float) finalPage, 1000);
                                    }
                                });

                                Elements showCards = document.getElementsByClass("card movie-card text-xs-center");

                                String showTitle = "";
                                int year = 0;

                                for(Element showCard : showCards){
                                    ArrayList<Country> countries = new ArrayList<>();
                                    Elements titleElement = showCard.getElementsByTag("a");
                                    Elements availableCountries = showCard.children().get(1).children();

                                    for(Element availCountry : availableCountries){
                                        if(!availCountry.hasClass("fa fa-plus")){
                                            Element flagElement = availCountry.child(0);
                                            Country country = new Country();

                                            if(flagElement.hasAttr("title")){
                                                country.setName(flagElement.attr("title"));
                                            }

                                            if(flagElement.hasAttr("src")){
                                                country.setFlagImageUrl("http://flixsearch.io" + flagElement.attr("src"));
                                            }

                                            countries.add(country);
                                        } else {
                                            if(availCountry.hasAttr("title")){
                                                String[] countryList = availCountry.attr("title").split(", ");

                                                for(String countryName : countryList){
                                                    Country country = new Country();
                                                    country.setName(countryName);
                                                    country.setFlagImageUrl(null);
                                                    countries.add(country);
                                                }
                                            }
                                        }

                                    }

                                    show.setCountries(countries);

                                    if(titleElement.hasAttr("title")){
                                        showTitle = titleElement.attr("title");
                                    }

                                    if(titleElement.hasAttr("href")){
                                        String detailsUrl = titleElement.attr("href");
                                        Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
                                        Matcher matcher = pattern.matcher(detailsUrl);

                                        if(matcher.find()){
                                            year = Integer.parseInt(matcher.group(0));
                                        }
                                    }

                                    if(showTitle.equals(show.getTitle()) && show.getYear() == year){
                                        show.setOnNetflix(true);
                                    }

                                }
                            }


                        }
                    }



                    page++;
                }

                if(!show.isOnNetflix()){
                    iterator.remove();
                }




                count++;
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            if(showList.isEmpty()){
                Toast.makeText(context, "None of the found shows are available on Netflix", Toast.LENGTH_LONG).show();
            } else {
                launchResultsActivity(showList);
            }
        }
    }

    private void launchResultsActivity(ArrayList<Show> shows){
        Intent intent = new Intent(getApplicationContext(), ResultListActivity.class);
        intent.putExtra("shows", shows);
        startActivity(intent);
    }

    private boolean isValidString(String string){
        string = string.replaceAll("[^a-zA-Z0-9-]", "");
        Pattern pattern = Pattern.compile("[a-zA-Z0-9-]");
        Matcher matcher = pattern.matcher(string);

        return matcher.find();
    }

    private String urlEncode(String string){
        return string.replaceAll(" ", "-");
    }

}
