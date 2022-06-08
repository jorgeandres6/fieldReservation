package edu.handong.android.soccerfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Reservations extends AppCompatActivity {

    DatabaseReference getData;
    int size;
    FirebaseAuth mAuth;
    Reserves[] AReservations = new Reserves[10];
    int i, k;
    LinearLayout LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        i = 0;
        k = 0;
        LL = findViewById(R.id.llReservations);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        getData = databaseReference.child("reservations/users").child(mAuth.getCurrentUser().getUid());

        getData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reserves reserve = snapshot.getValue(Reserves.class);
                    AReservations[i] = reserve;
                    i++;
                    k++;
                }

                i=0;

                for (int j=0; j<k; j++){
                    add();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void add(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference ref = databaseReference.child("reservations/users").child(mAuth.getCurrentUser().getUid()).child(AReservations[i].getReservationID());

        //DatabaseReference ref2 = databaseReference.child("reservations/users").child(mAuth.getCurrentUser().getUid()).child(Integer.toString(AReservations[i].getID_R()));


        View v = getLayoutInflater().inflate(R.layout.card_reservation,null);
        v.setTag(i);
        TextView tvTitle = v.findViewById(R.id.tvTitleCR);
        TextView tvHour = v.findViewById(R.id.tvHourCR);
        TextView tvCost = v.findViewById(R.id.tvCostCR);
        Button btnCancel = v.findViewById(R.id.btnCancel_reservation);

        tvTitle.setText(AReservations[i].getFname());
        tvHour.setText(Integer.toString(AReservations[i].getHour()));
        tvCost.setText(Integer.toString(AReservations[i].getTotal()));

        LL.addView(v);



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ref.removeValue();
                //Toast.makeText(getApplicationContext(),AReservations[1].getReservationID(),Toast.LENGTH_SHORT).show();
            }

        });
        i++;
    }
}

class Reserves {
    private int hour;
    private String Fname;
    private String id;
    private int total;
    private String reservationID;

    public String getFname() {
        return Fname;
    }

    public void setFname(String Fname) {
        this.Fname = Fname;
    }

     public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setID_R(String reservationID) {
        this.reservationID = reservationID;
    }
}