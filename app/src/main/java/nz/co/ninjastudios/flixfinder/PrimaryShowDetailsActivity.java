package nz.co.ninjastudios.flixfinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.ImageViewBitmapInfo;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PrimaryShowDetailsActivity extends AppCompatActivity {

    private Gson gson;
    private Context context;
    private String api_key = "5549383f29245415046c05c039ef2009";
    private ImageView imageViewPoster;
    private TextView textViewShowTitle;
    private TextView textViewRuntime;
    private TextView textViewRating;
    private TextView textViewGenres;
    private TextView textViewOverview;

    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_show_details);

        context = this;
        gson = new Gson();

        imageViewPoster = (ImageView) findViewById(R.id.imageViewPoster);

        show = (Show) getIntent().getSerializableExtra("show");

        new GetPosterImageUrlTask(show.getPoster_path()).execute();

        textViewShowTitle = (TextView) findViewById(R.id.textViewShowTitle);
        textViewRuntime = (TextView) findViewById(R.id.textViewRuntime);
        textViewRating = (TextView) findViewById(R.id.textViewRating);
        textViewGenres = (TextView) findViewById(R.id.textViewGenres);
        textViewOverview = (TextView) findViewById(R.id.textViewOverview);

        textViewShowTitle.setText(show.getTitle());

        if(this.show instanceof Movie){
            Movie movie = (Movie) show;
            textViewRuntime.setText(String.valueOf(movie.getRuntime()) + " minutes");
        } else {
            textViewRuntime.setText("N/A");
        }

        textViewRating.setText(show.getVote_average() + "/10 - " + show.getVote_count() + " votes");

        String genreString = "";

        for(Genre genre : show.getGenres()){
            genreString += genre.getName() + ", ";
        }

        genreString = genreString.substring(0, genreString.length() - 2);

        textViewGenres.setText(genreString);
        textViewOverview.setText(show.getOverview());
    }



    private class GetPosterImageUrlTask extends AsyncTask<Void, Void, Void> {

        private String posterPath;

        public GetPosterImageUrlTask(String posterPath){
            this.posterPath = posterPath;
        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Ion.with(context)
                    .load("https://api.themoviedb.org/3/configuration?api_key=" + api_key)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            JsonObject images = result.getAsJsonObject("images");
                            String base_url = images.get("base_url").getAsString();
                            String poster_size = "";
                            String[] poster_sizes = gson.fromJson(images.getAsJsonArray("backdrop_sizes"), String[].class);

                            for(String size : poster_sizes){
                                if(size.equals("original")){
                                    poster_size = size;
                                }
                            }

                            base_url += poster_size + posterPath;
                            loadPosterImage(base_url);
                        }
                    });


            return null;
        }

        @Override
        protected void onPostExecute(Void result){

        }
    }

    private class LoadPosterImageTask extends AsyncTask<Void, Void, Bitmap> {

        private String imageUrl;

        public LoadPosterImageTask(String imageUrl){
            this.imageUrl = imageUrl;
        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            super.onPostExecute(result);

            imageViewPoster.setImageBitmap(result);
        }
    }

    private void loadPosterImage(String url){
        new LoadPosterImageTask(url).execute();
    }
}
