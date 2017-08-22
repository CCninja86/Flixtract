package nz.co.ninjastudios.flixfinder;

/**
 * Created by james on 21/08/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

/**
 * Created by James on 30/11/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    private ArrayList<Show> items;
    private  ArrayList<Integer> selectedItems;
    private int layout;

    public ListViewAdapter(Activity context, ArrayList<Show> items, int layout){
        this.layout = layout;
        this.items = items;
        this.selectedItems = new ArrayList<>();
    }

    public ArrayList<Integer> getSelectedItems(){
        return  this.selectedItems;
    }

    public void toggleSelected(Integer position){
        if(selectedItems.contains(position)){
            selectedItems.remove(position);
        } else {
            selectedItems.add(position);
        }
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Show getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
        }

        String title = items.get(position).getTitle();
        int year = items.get(position).getYear();
        if(title != null){
            TextView textView = (TextView) convertView.findViewById(R.id.textViewItem);

            if(textView != null){
                textView.setText(title + " (" + year + ")");
            }
        }

        if(selectedItems.contains(position)){
            convertView.setSelected(true);
            convertView.setPressed(true);
            convertView.setBackgroundColor(Color.LTGRAY);
        } else {
            convertView.setSelected(false);
            convertView.setPressed(false);
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return  convertView;
    }

}