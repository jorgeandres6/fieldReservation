package edu.handong.android.soccerfield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SoccerFieldInfo extends AppCompatActivity {

    //DECLARE VARIABLES
    int opening;
    int close;
    String address;
    String name;
    String img;
    String id;
    int cost,total;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_field_info);

        //INITIALIZE VARIABLES
        address="";
        name="";
        img="";
        cost=0;
        total=0;
        id="";

        //REFERENCE TO FIREBASE AUTHORIZATION
        mAuth = FirebaseAuth.getInstance();

        //REFERENCE TO UI ELEMENTS
        TextView tvName = findViewById(R.id.tvFnameI);
        TextView tvAddress = findViewById(R.id.tvFaddressI);
        TextView tvCost = findViewById(R.id.tvFcostI);
        TextView tvTotal = findViewById(R.id.tvTotalI);
        TextView tvOHours = findViewById(R.id.tvOHour);
        ImageView Fimg = findViewById(R.id.ivFimgI);
        Button btnReserve = findViewById(R.id.btnMakeReservation);

        //RETRIEVE INFORMATION FROM PREVIOUS ACTIVITY
        Bundle extras = getIntent().getExtras();
        if (extras != null) { //CHECH IF THERE'S ANY DATA SENT
            opening = extras.getInt("opening"); //OPENING HOUR
            close = extras.getInt("close"); //CLOSING HOUR
            address = extras.getString("address"); //FIELD ADDRESS
            name = extras.getString("name"); //FIELD NAME
            img = extras.getString("img"); //FIELD IMAGE
            cost = extras.getInt("cost"); //COST PER HOUR
            id = extras.getString("id"); //FIELD ID
            total = cost;
        }

        //SET DATA INTO UI ELEMENTS
        tvName.setText(name); //FIELD NAME
        tvAddress.setText(address); //FIELD ADDRESS
        tvCost.setText(cost+" USD/hour"); //COST PER HOUR
        tvTotal.setText("Total to pay: "+total+" USD"); //TOTAL VALUE TO PAY - FURTHER IMPLEMENTATION FOR MULTIPLE HOURS RESERVATION
        tvOHours.setText("Operating hours: "+opening+" ~ "+close); //OPERATING SHCEDULE

        Picasso.get().load(img).into(Fimg);//RENDER IMAGE

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                FirebaseUser user = mAuth.getCurrentUser(); //REFERENCE TO CURRENT USER USING FIREBASE METHOD
                if (user == null){ //IF NO USER IS LOGGED IN
                    intent = new Intent(getApplicationContext(),LoginAdmin.class); //LAUNCH LOGIN ACTIVITY
                }else{ //IF THERE'S A USER LOGGED IN
                    intent = new Intent(getApplicationContext(),ReserveConfirm.class); //LAUNCH RESERVE CONFIRMATION ACTIVITY

                    // PASS DATA TO NEXT ACTIVITY
                    intent.putExtra("name", name); //FIELD NAME
                    intent.putExtra("address", address); //FIELD ADDRESS
                    intent.putExtra("total", total); //TOTAL VALUE TO PAY
                    intent.putExtra("id", id); //FIELD ID
                    intent.putExtra("opening", opening); //OPENING HOUR
                    intent.putExtra("closing", close); //CLOSING HOUR
                }
                    startActivity(intent);
            }
        });
    }
}