package edu.handong.android.soccerfield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class ReserveConfirm extends AppCompatActivity {

    String address, name;
    int total;
    TextView tvAddress, tvFname, tvTotal;
    Button btnReserve;
    DatabaseReference databaseReference;
    String timestamp;
    FirebaseAuth mAuth;

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            address = extras.getString("address");
            name = extras.getString("name");
            total = extras.getInt("total");
            //The key argument here must match that used in the other activity
        }

        tvFname.setText(name);
        tvAddress.setText(address);
        tvTotal.setText("Your total bill is: "+total+" USD");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("reservations");

        mAuth = FirebaseAuth.getInstance();

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long currentTime = System.currentTimeMillis()/1000;
                timestamp = currentTime.toString();
                databaseReference.child(mAuth.getCurrentUser().getUid()).child(timestamp).setValue(new Reservation(mAuth.getCurrentUser().getEmail(),"0",name,total));

                Intent intent = new Intent(getApplicationContext(),ReserveOK.class);
                startActivity(intent);
            }
        });
    }
}

class Reservation {
    public String email;
    public String ID;
    public String Fname;
    public String hour;
    public int total;

    public Reservation (String email, String ID, String Fname, int total){
        this.email = email;
        this.ID = ID;
        this.Fname = Fname;
        this.total = total;
        this.hour = "09h00";
    }
}