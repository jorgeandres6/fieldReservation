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
    Button btncheckfield,btnloginm,btnlogout,btnsigninm,btnCheckReservations, btnAddField, btnReserve, btnLogin, btnSignin, btnLogout;
    ToggleButton tbLanguage;
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;
    FirebaseAuth mAuth;

    String[] cities = {"Quito","Ibarra"}; //AVAILABLE CITIES ARRAY
    String selectedCity = "Quito"; //SELECTED CITY VARIABLE, DEFAULT VALUE "QUITO"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   loadLocale();
        setContentView(R.layout.activity_main);

        //REFERENCE TO UI ELEMENTS
        Menu=findViewById(R.id.menu);
        textview=findViewById(R.id.textView);
        btncheckfield=findViewById(R.id.btnCheckField);
        btnloginm=findViewById(R.id.btnLogInM);
        btnlogout=findViewById(R.id.btnLogout);
        btnsigninm=findViewById(R.id.btnSignInM);
        tbLanguage=findViewById(R.id.tbLanguage);
        btnCheckReservations = findViewById(R.id.btnCheckReservation);
        btnAddField = findViewById(R.id.btnAddFieldM);
        btnReserve = findViewById(R.id.btnCheckField);
        btnLogin = findViewById(R.id.btnLogInM);
        btnSignin = findViewById(R.id.btnSignInM);
        btnLogout = findViewById(R.id.btnLogout);

        //ASSIGNING VALUES TO VARIABLES
        autoCompleteText = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this,R.layout.items,cities);
        mAuth = FirebaseAuth.getInstance();

        btnAddField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Update_sf_info.class); //LAUNCH ACTIVITY FOR ADDING A NEW FIELD
                startActivity(intent);
            }
        });

        btnCheckReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Reservations.class); //LAUNCH RESERVATION MADE CHECKING ACTIVITY
                startActivity(intent);
            }
        });

        tbLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //SELECTING BETWEEN DIFFERENT LANGUAGES
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    setLocale("es");
                }else{
                    setLocale("en");
                }
            }
        });

       btnReserve.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),SF_list.class); //LAUNCH LIST OF SOCCER FIELDS ACTIVITIES
               intent.putExtra("city",selectedCity.toLowerCase()); //PASS CITY SELECTED
               startActivity(intent);
           }
       });

       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),LoginAdmin.class); //LAUNCH LOGIN ACTIVITY
               startActivity(intent);
           }
       });

       btnSignin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish(); //END ACTIVITY FOR REFRESHMENT
               Intent intent = new Intent(getApplicationContext(),Signin_ad.class); //LAUNCH SIGN IN ACTIVITY
               startActivity(intent);
           }
       });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut(); //LOGOUT USING FIREBASE METHOD
                Intent intent = getIntent();
                finish(); // FINISH ACTIVITY
                startActivity(intent); //FOR REFRESHMENT
            }
        });

       autoCompleteText.setAdapter(adapterItems); //FOR POPULATING THE DROPDOWN LIST

       autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               selectedCity = parent.getItemAtPosition(position).toString(); //GET THE SELECTED CITY IN THE DROPDOWN LIST
           }
       });

    }

    @Override
    protected void onResume() {

        //REFER TO UI ELEMENTS
        Button btnLogin = findViewById(R.id.btnLogInM);
        Button btnSignin = findViewById(R.id.btnSignInM);
        Button btnLogout = findViewById(R.id.btnLogout);

        super.onResume();

        if (mAuth.getCurrentUser() == null){ //DEPENDING IF THE USER IS LOGGED IN SOME BUTTONS ARE SHOWN AND OTHERS NOT
            btnLogin.setVisibility(View.VISIBLE);
            btnSignin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            btnCheckReservations.setVisibility(View.GONE);
            btnAddField.setVisibility(View.GONE);
        }else{
            btnLogin.setVisibility(View.GONE);
            btnSignin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            btnCheckReservations.setVisibility(View.VISIBLE);
            btnAddField.setVisibility(View.VISIBLE);
        }
    }

    private void setLocale(String language) { //SETTING LANGUAGE CONFIG
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