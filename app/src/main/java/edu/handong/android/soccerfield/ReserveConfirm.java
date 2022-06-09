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

        address = "";
        name = "";
        total = 0;
        tvAddress = findViewById(R.id.tvAddressR);
        tvFname = findViewById(R.id.tvFnameR);
        tvTotal = findViewById(R.id.tvTotalR);
        btnReserve = findViewById(R.id.btnReserveR);
        TimePicker = findViewById(R.id.timePicker1);
        TimePicker.setIs24HourView(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            address = extras.getString("address");
            name = extras.getString("name");
            total = extras.getInt("total");
            id = extras.getString("id");
            opening = extras.getInt("opening");
            closing = extras.getInt("closing");
            //The key argument here must match that used in the other activity
        }

        tvFname.setText(name);
        tvAddress.setText(address);
        tvTotal.setText("Your total bill is: "+total+" USD");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("reservations/users");
        databaseReferenceFields = firebaseDatabase.getReference("reservations/fields");

        mAuth = FirebaseAuth.getInstance();

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedHour = TimePicker.getCurrentHour();
                databaseReferenceFields.child(id).child(Integer.toString(selectedHour)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null){
                            if (selectedHour > opening && selectedHour < closing){
                                Long currentTime = System.currentTimeMillis()/1000;
                                timestamp = currentTime.toString();
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("r"+timestamp).setValue(new Reservation(mAuth.getCurrentUser().getEmail(),id,name,total,selectedHour,"r"+timestamp));
                                databaseReferenceFields.child(id).child(Integer.toString(selectedHour)).setValue(new ReservationFields(mAuth.getCurrentUser().getEmail()));
                                Intent intent = new Intent(getApplicationContext(),ReserveOK.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(ReserveConfirm.this, "Please, select an hour within the opening a closing times", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ReserveConfirm.this, "Sorry, that time is not available", Toast.LENGTH_SHORT).show();
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

class Reservation {
    public String email;
    public String ID;
    public String Fname;
    public int hour;
    public int total;
    public String reservationID;

    public Reservation (String email, String ID, String Fname, int total, int hour, String rID){
        this.email = email;
        this.ID = ID;
        this.Fname = Fname;
        this.total = total;
        this.hour = hour;
        this.reservationID = rID;
    }
}

class ReservationFields {
    public String user;

    public ReservationFields (String user){
        this.user = user;
    }
}