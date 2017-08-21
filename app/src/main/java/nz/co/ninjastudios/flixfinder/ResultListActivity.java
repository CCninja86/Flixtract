package nz.co.ninjastudios.flixfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        ListView listViewResults = (ListView) findViewById(R.id.listResults);

        Intent intent = getIntent();
        ArrayList<String> results = intent.getStringArrayListExtra("results");

        ListViewAdapter listViewAdapter = new ListViewAdapter(this, results, R.layout.row);
        listViewResults.setAdapter(listViewAdapter);

    }
}
