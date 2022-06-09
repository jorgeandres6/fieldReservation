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

import java.util.ArrayList;
import java.util.List;

public class Reservations extends AppCompatActivity {

    //DECLARE VARIABLES
    DatabaseReference getData;
    FirebaseAuth mAuth;
    List <Reserves> AReservations = new ArrayList<Reserves>();
    LinearLayout LL;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        //ASSIGN VALUES TO VARIABLES
        mAuth = FirebaseAuth.getInstance(); //REFERENCE FOR FIREBASE AUTHENTICATION
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); //REFERENCE TO FIREBASE RT-DATABASE
        DatabaseReference databaseReference = firebaseDatabase.getReference(); //REFERENCE TO FIREBASE RT-DATABASE
        getData = databaseReference.child("reservations/users").child(mAuth.getCurrentUser().getUid()); //REFERENCE TO A SPECIFIC BRANCH WITHIN THE FIREBASE RT-DB

        //REFER TO UI ELEMENTS
        LL = findViewById(R.id.llReservations);
        btnCancel = findViewById(R.id.btnOk);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            } //END ACTIVITY TO RETURN TO PREVIOUS ACTIVITY
        });

        getData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //LOOP THROUGH ALL THE CHILD ELEMENTS WITHIN THE SPECIFIC BRANCH OF RESERVATIONS FROM USERS
                    Reserves reserve = snapshot.getValue(Reserves.class); //GET A CHILD ELEMENT
                    AReservations.add(reserve); //POPULATE AN ARRAY LIST FOR FURTHER ITERATION
                }

                for (int j=0; j<AReservations.size(); j++){
                    add(j); //ADDING ELEMENTS TO THE RESERVATION LIST AND DISPLAYING
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void add(int i){

        //REFERENCE TO FIREBASE
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference ref = databaseReference.child("reservations/users").child(mAuth.getCurrentUser().getUid()).child(AReservations.get(i).getReservationID()); //REFERENCE TO RESERVATIONS MADE FROM USERS
        DatabaseReference ref2 = databaseReference.child("reservations/fields").child(AReservations.get(i).getID()).child(Integer.toString(AReservations.get(i).getHour())); //REFERENCE TO FIELDS RESERVATIONS BY HOUR

        View v = getLayoutInflater().inflate(R.layout.card_reservation,null); //USE DE CARD UI RESOURCE FILE TO POPULATE THE VIEW
        v.setTag(i); //IDENTIFY THE CARD

        //REFERENCE TO THE UI ELEMENTS OF THE CARD RESOURCE
        TextView tvTitle = v.findViewById(R.id.tvTitleCR);
        TextView tvHour = v.findViewById(R.id.tvHourCR);
        TextView tvCost = v.findViewById(R.id.tvCostCR);
        Button btnCancel = v.findViewById(R.id.btnCancel_reservation);

        //SET VALUES TO UI ELEMENTS
        tvTitle.setText(AReservations.get(i).getFname());
        tvHour.setText(Integer.toString(AReservations.get(i).getHour()));
        tvCost.setText(Integer.toString(AReservations.get(i).getTotal()));

        LL.addView(v); //ADD THE CARD TO THE LAYOUT

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.removeValue(); //REMOVE THE RESERVATION FROM THE USERS BRANCH AT FIREBASE RT-DB
                ref2.removeValue(); //REMOVE THE RESERVATION FROM THE FIELDS BRANCH AT FIREBASE RE-DB
                Toast.makeText(Reservations.this, "Reservation cancelled successfully", Toast.LENGTH_SHORT).show(); //SUCCESSFUL RESERVATION CANCELING MESSAGE
                finish(); //FINISH THE ACTIVITY AND RETURN TO PREVIOUS ACTIVITY
            }
        });
    }
}

class Reserves { //CLASS NEEDED TO RETRIEVE DATA FROM FIREBASE RT-DATABASE

    //DECLARING VARIABLES ACCORDING TO THE FIREBASE RT-DB FIELDS NAMES
    private int hour; //HOUR RESERVED
    private String Fname; //FIELD NAME
    private String ID; //FIELD ID
    private int total; //TOTAL TO PAY PER HOUR
    private String reservationID; //RESERVATION ID

    //GETTERS AND SETTERS
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

    public String getID() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
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