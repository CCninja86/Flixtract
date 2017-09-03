package nz.co.ninjastudios.flixfinder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by james on 28/08/2017.
 */

public class GenreSpinAdapter extends ArrayAdapter<Genre> {


    private Context context;
    private Genre[] values;

    public GenreSpinAdapter(Context context, int textViewResourceId,
                       Genre[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.length;
    }

    public Genre getItem(int position){
        return values[position];
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getName());

        return label;
    }
}