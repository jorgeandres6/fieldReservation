package edu.handong.android.soccerfield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String[] cities = {"Quito","Ibarra","Pohang","Daegu"};

    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       autoCompleteText = findViewById(R.id.auto_complete_text);

       adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);

       autoCompleteText.setAdapter(adapterItems);

       autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String item = parent.getItemAtPosition(position).toString();
               Toast.makeText(getApplicationContext(),"Item "+item,Toast.LENGTH_SHORT).show();
           }
       });


    }


}