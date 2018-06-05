package nz.co.ninjastudios.flixfinder;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.util.List;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Show> shows;

    private ItemClickListener itemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView poster;
        public TextView title;
        public TextView rating;

        public MyViewHolder(View view) {
            super(view);
            poster = view.findViewById(R.id.imageViewCardShowPoster);
            rating = view.findViewById(R.id.textViewCardRating);
            title = view.findViewById(R.id.textViewCardTitle);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickListener != null){
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }


    public ShowsAdapter(Context mContext, List<Show> shows) {
        this.mContext = mContext;
        this.shows = shows;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_card, parent, false);

        return new MyViewHolder(itemView);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Show show = shows.get(position);

        Ion.with(holder.poster)
                .load(show.getPoster_path());

        holder.title.setText(show.getTitle());
        holder.rating.setText(String.valueOf(show.getVote_average()));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }
}