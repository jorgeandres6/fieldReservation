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

    String[] cities = {"Quito","Ibarra"};
    String selectedCity = "Quito";

    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       autoCompleteText = findViewById(R.id.auto_complete_txt);

        
       adapterItems = new ArrayAdapter<String>(this,R.layout.items,cities);

       Button btnReserve = findViewById(R.id.btnCheckField);
       Button btnLogin = findViewById(R.id.btnLogInM);
       Button btnSignin = findViewById(R.id.btnSignInM);

       btnReserve.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),SF_list.class);
               intent.putExtra("city",selectedCity.toLowerCase(Locale.ROOT));
               startActivity(intent);
           }
       });

       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),LoginAdmin.class);
               startActivity(intent);
           }
       });

       btnSignin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),Signin_ad.class);
               startActivity(intent);
           }
       });

       autoCompleteText.setAdapter(adapterItems);

       autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               selectedCity = parent.getItemAtPosition(position).toString();
               //Toast.makeText(getApplicationContext(),"City: "+city,Toast.LENGTH_SHORT).show();
           }
       });


    }


}