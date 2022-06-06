package edu.handong.android.soccerfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Initialize variable
    TextView Menu, textview;
    Button btncheckfield,btnloginm,btnlogout,btnsigninm,btnCheckReservations;
    ToggleButton tbLanguage;

    String[] cities = {"Quito","Ibarra"};
    String selectedCity = "Quito";

    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   loadLocale();
        setContentView(R.layout.activity_main);

        //Assign variable
        Menu=findViewById(R.id.menu);
        textview=findViewById(R.id.textView);
        btncheckfield=findViewById(R.id.btnCheckField);
        btnloginm=findViewById(R.id.btnLogInM);
        btnlogout=findViewById(R.id.btnLogout);
        btnsigninm=findViewById(R.id.btnSignInM);
        tbLanguage=findViewById(R.id.tbLanguage);
        btnCheckReservations = findViewById(R.id.btnCheckReservation);

        btnCheckReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Reservations.class);
                startActivity(intent);
            }
        });

        tbLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    setLocale("es");
                }else{
                    setLocale("en");
                }
            }
        });

       autoCompleteText = findViewById(R.id.auto_complete_txt);

       adapterItems = new ArrayAdapter<String>(this,R.layout.items,cities);

       mAuth = FirebaseAuth.getInstance();

       Button btnReserve = findViewById(R.id.btnCheckField);
       Button btnLogin = findViewById(R.id.btnLogInM);
       Button btnSignin = findViewById(R.id.btnSignInM);
       Button btnLogout = findViewById(R.id.btnLogout);

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
               finish();
               Intent intent = new Intent(getApplicationContext(),Signin_ad.class);
               startActivity(intent);
           }
       });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = getIntent();
                finish();
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

    @Override
    protected void onResume() {

        Button btnLogin = findViewById(R.id.btnLogInM);
        Button btnSignin = findViewById(R.id.btnSignInM);
        Button btnLogout = findViewById(R.id.btnLogout);

        super.onResume();
        if (mAuth.getCurrentUser() == null){
            btnLogin.setVisibility(View.VISIBLE);
            btnSignin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            btnCheckReservations.setVisibility(View.GONE);
        }else{
            btnLogin.setVisibility(View.GONE);
            btnSignin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            btnCheckReservations.setVisibility(View.VISIBLE);
        }
    }

    private void setLocale(String language) {
        //Initialize resources
        Resources resources = getResources();
        //Initialize metrics
        DisplayMetrics metrics = resources.getDisplayMetrics();
        //Initialize locale
        Configuration configuration = resources.getConfiguration();
        //Update configuration
        configuration.locale = new Locale(language);
        //update configuration
        resources.updateConfiguration(configuration,metrics);
        //Notify configuration
        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        //set strings from resources
        Menu.setText(R.string.menu);
        textview.setText(R.string.selection_info);
        btncheckfield.setText(R.string.CheckField);
        btnsigninm.setText(R.string.Sign_in);
        btnlogout.setText(R.string.LogOut);
        btnloginm.setText(R.string.Log_in);
    }


}