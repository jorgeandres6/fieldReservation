package edu.handong.android.soccerfield;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
        loadLocale();
        setContentView(R.layout.activity_main);

        Button changeLang = findViewById(R.id.changeLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show AlertDialog to display list of languages, one can be selected
                showChangeLanguageDialog();
            }
        });

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

    private void showChangeLanguageDialog() {
        //array of languages on menu
        final String[] listItems = {"English", "Español"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("esp");
                    recreate();
                }
                else if (i == 1) {
                    setLocale("en");
                    recreate();
                }
                else if (i == 2) {
                    setLocale("kr");
                    recreate();
                }

                dialogInterface.dismiss();
            }

        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale= new Locale(lang);
        Locale.setDefault(locale);
        Configuration config= new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=prefs.getString("My_Lang", "");
        setLocale(language);

    }


}