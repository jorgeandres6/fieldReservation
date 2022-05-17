package edu.handong.android.soccerfield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String[] cities = {"Quito","Ibarra"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);

        ListView lvFields = (ListView) findViewById(R.id.lvFields);

        lvFields.setAdapter(adapter);

        lvFields.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), cities[i].toLowerCase(Locale.ROOT), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),SF_list.class);
                intent.putExtra("city",cities[i].toLowerCase(Locale.ROOT));
                startActivity(intent);
            }
        });
    }


}