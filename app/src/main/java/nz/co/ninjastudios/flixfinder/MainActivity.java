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
                new SearchShowsTask(searchTerm).execute();
            }
        });
    }




    private class SearchShowsTask extends AsyncTask<Void, Void, Void> {

        private String searchTerm;
        private ArrayList<String> results;

        public SearchShowsTask(String searchTerm){
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

                for(Element showCard : showCards){
                    Elements titleElement = showCard.getElementsByTag("a");

                    if(titleElement.hasAttr("title")){
                        String showTitle = titleElement.attr("title");
                        results.add(showTitle);
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

    private void launchResultsActivity(ArrayList<String> results){
        Intent intent = new Intent(getApplicationContext(), ResultListActivity.class);
        intent.putStringArrayListExtra("results", results);
        startActivity(intent);
    }
}
