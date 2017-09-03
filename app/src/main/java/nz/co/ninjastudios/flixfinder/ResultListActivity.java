package nz.co.ninjastudios.flixfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ResultListActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private String api_key = "5549383f29245415046c05c039ef2009";
    private Gson gson;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        gson = new Gson();
        context = this;

        final ListView listViewResults = (ListView) findViewById(R.id.listResults);

        Intent intent = getIntent();
        ArrayList<Show> results = (ArrayList<Show>) intent.getSerializableExtra("shows");

        final ListViewAdapter listViewAdapter = new ListViewAdapter(this, results, R.layout.row);
        listViewResults.setAdapter(listViewAdapter);

        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Show show = (Show) listViewResults.getItemAtPosition(position);
            }
        });

    }

    private void getShowDetails(Show show){
        new GetShowDetailsTask(show).execute();
    }



    private class GetShowDetailsTask extends AsyncTask<Void, Void, Void> {

        private Show show;
        private Movie movie;
        private TVShow tvShow;

        public GetShowDetailsTask(Show show){
            this.show = show;
            movie = null;
            tvShow = null;
        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Ion.with(context)
            .load("https://api.themoviedb.org/3/movie/" + show.getId() + "?api_key=" + api_key)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    if(show instanceof Movie){
                        movie = (Movie) show;
                    } else if(show instanceof TVShow){
                        tvShow = (TVShow) show;
                    }


                    Genre[] genres = gson.fromJson(result.getAsJsonArray("genres"), Genre[].class);
                    String overview = result.get("overview").getAsString();
                    String poster_path = result.get("poster_path").getAsString();
                    double vote_average = result.get("vote_average").getAsDouble();
                    int vote_count = result.get("vote_count").getAsInt();
                    boolean adult = result.get("adult").getAsBoolean();
                    int budget = result.get("budget").getAsInt();
                    String release_date = result.get("release_date").getAsString();
                    int revenue = result.get("revenue").getAsInt();
                    int runtime = result.get("runtime").getAsInt();
                    String title = result.get("title").getAsString();
                    String status = result.get("status").getAsString();

                    movie.setGenres(genres);
                    movie.setOverview(overview);
                    movie.setPoster_path(poster_path);
                    movie.setVote_average(vote_average);
                    movie.setVote_count(vote_count);
                    movie.setAdult(adult);
                    movie.setBudget(budget);
                    movie.setRelease_date(release_date);
                    movie.setRevenue(revenue);
                    movie.setRuntime(runtime);
                    movie.setTitle(title);
                    movie.setStatus(status);

                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void result){

        }
    }
}
