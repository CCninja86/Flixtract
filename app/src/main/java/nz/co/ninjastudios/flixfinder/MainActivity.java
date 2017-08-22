package nz.co.ninjastudios.flixfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSearch);
        editText = (EditText) findViewById(R.id.editTextSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = editText.getText().toString();
                new SearchNetflixShowsTask(searchTerm).execute();
            }
        });
    }




    private class SearchNetflixShowsTask extends AsyncTask<Void, Void, Void> {

        private String searchTerm;
        private ArrayList<Show> results;

        public SearchNetflixShowsTask(String searchTerm){
            this.searchTerm = searchTerm;
            this.results = new ArrayList<>();
        }

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String urlString = "http://flixsearch.io/search/" + searchTerm;
            Document document = null;

            try {
                document = Jsoup.connect(urlString).get();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(document != null){
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

                    if(titleElement.hasAttr("title")){
                        showTitle = titleElement.attr("title");
                    }

                    if(titleElement.hasAttr("href")){
                        urlString = titleElement.attr("href");
                        Document documentDetails = null;

                        try {
                            documentDetails = Jsoup.connect(urlString).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(documentDetails != null){
                            Elements yearElements = documentDetails.select("small");
                            Elements leadElements = documentDetails.getElementsByClass("lead");
                            Element showTypeElement = null;
                            Show show = null;

                            for(Element element : leadElements){
                                if(element.text().contains("movie")){
                                    show = new Movie();
                                    break;
                                } else if(element.text().contains("TV show")){
                                    show = new TVShow();
                                    break;
                                }
                            }


                            String yearString = yearElements.get(0).text();
                            year = Integer.parseInt(yearString.substring(1, yearString.length() - 1));
                            show.setTitle(showTitle);
                            show.setYear(year);
                            show.setCountries(countries);

                            results.add(show);
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            progressBar.setVisibility(View.INVISIBLE);
            launchResultsActivity(this.results);
        }
    }

    private void launchResultsActivity(ArrayList<Show> shows){
        Intent intent = new Intent(getApplicationContext(), ResultListActivity.class);
        intent.putExtra("shows", shows);
        startActivity(intent);
    }
}
