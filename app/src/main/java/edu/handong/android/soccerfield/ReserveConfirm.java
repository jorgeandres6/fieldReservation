package edu.handong.android.soccerfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ReserveConfirm extends AppCompatActivity {

    //DECLARE VARIABLES
    String address,name,id;
    int total, selectedHour, opening, closing;
    TextView tvAddress, tvFname, tvTotal;
    Button btnReserve;
    DatabaseReference databaseReference, databaseReferenceFields;
    String timestamp;
    FirebaseAuth mAuth;
    TimePicker TimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_confirm);

        // INITIALIZE VARIABLES
        address = "";
        name = "";
        total = 0;

        //REFERENCE TOO UI ELEMENTS
        tvAddress = findViewById(R.id.tvAddressR);
        tvFname = findViewById(R.id.tvFnameR);
        tvTotal = findViewById(R.id.tvTotalR);
        btnReserve = findViewById(R.id.btnReserveR);
        TimePicker = findViewById(R.id.timePicker1);

        //REFERENCES TO FIREBASE RT-DB
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("reservations/users"); //USERS RESERVATIONS BRANCH
        databaseReferenceFields = firebaseDatabase.getReference("reservations/fields"); //FIELDS RESERVATIONS BRANCH

        //REFERENCE TO FIREBASE AUTHENTICATION
        mAuth = FirebaseAuth.getInstance();

        //SET 24 HOUR MODE FOR THE TIMEPICKER
        TimePicker.setIs24HourView(true);

        //RETRIEVE DATA FROM PREVIOUS ACTIVITY
        Bundle extras = getIntent().getExtras();
        if (extras != null) { //IF THERE'S ANY DATA PASSED
            address = extras.getString("address"); //FIEL ADDRESS
            name = extras.getString("name"); //FIELD NAME
            total = extras.getInt("total"); //COST PER HOUR
            id = extras.getString("id"); //FIELD ID
            opening = extras.getInt("opening"); //OPENING HOUR
            closing = extras.getInt("closing"); //CLOSING HOUR
        }

        //DISPLAY DATA
        tvFname.setText(name); //FIELD NAME
        tvAddress.setText(address); //FIELD ADDRESS
        tvTotal.setText("Your total bill is: "+total+" USD"); //OPERATING SCHEDULE

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedHour = TimePicker.getCurrentHour(); //GET SELECTED HOUR IN THE TIMEPICKER
                databaseReferenceFields.child(id).child(Integer.toString(selectedHour)).addListenerForSingleValueEvent(new ValueEventListener() { //LISTENER FOR FIREBASE RT-DB ACTIONS
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null){ //VERIFY IF AN SPECIFIC HOUR OF A FIELD IS RESERVED
                            if (selectedHour > opening && selectedHour < closing){ //VERIFY IF THE THE USER IS MAKING A RESERVATION WITHIN THE OPERATING SCHEDULE
                                Long currentTime = System.currentTimeMillis()/1000; //GET THE CURRENT TIME IN MS
                                timestamp = currentTime.toString(); //TRANSFORM THE CURRENT TIME TO STRING
                                //REFERENCE TO FIREBASE RT-DB
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("r"+timestamp).setValue(new Reservation(mAuth.getCurrentUser().getEmail(),id,name,total,selectedHour,"r"+timestamp)); //CREATE A RESERVATION AT THE USER'S BRANCH
                                databaseReferenceFields.child(id).child(Integer.toString(selectedHour)).setValue(new ReservationFields(mAuth.getCurrentUser().getEmail())); //CREATE A RESERVATION AT THE FIELD'S BRANCH
                                Intent intent = new Intent(getApplicationContext(),ReserveOK.class); //LAUNCH SUCCESSFUL RESERVATION ACTIVITY
                                startActivity(intent);
                            } else{
                                Toast.makeText(ReserveConfirm.this, "Please, select an hour within the opening a closing times", Toast.LENGTH_SHORT).show(); //ERROR MESSAGE IF SELECTED HOUR IS OUTSIDE THE OPERATING SCHEDULE
                            }
                        } else {
                            Toast.makeText(ReserveConfirm.this, "Sorry, that time is not available", Toast.LENGTH_SHORT).show(); //ERROR MESSAGE IF THE SELECTED HOUR IS ALREADY RESERVED
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}

class Reservation { //CLASS FOR SAVING DATA ON FIREBASE RT-DB - USERS' RESERVATIONS BRANCH

    //DECLARING VARIABLES ACCORDING TO FIREBASE RT-DB FIELDS' NAMES
    public String email;
    public String ID;
    public String Fname;
    public int hour;
    public int total;
    public String reservationID;

    //CONSTRUCTOR
    public Reservation (String email, String ID, String Fname, int total, int hour, String rID){
        this.email = email;
        this.ID = ID;
        this.Fname = Fname;
        this.total = total;
        this.hour = hour;
        this.reservationID = rID;
    }
}

class ReservationFields { //CLASS FOR SAVING DATA INTO FIREBASE RT-DB FIELDS' RESERVATIONS BRANCH

    //DECLARING VARIABLES ACCORDING TO FIREBASE RT-DB FIELDS' NAMES
    public String user;

    //CONSTRUCTORS
    public ReservationFields (String user){
        this.user = user;
    }
}