package edu.handong.android.soccerfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

public class SF_list extends AppCompatActivity {

    LinearLayout LL;
    String [] nombres = {"1","2","3"};
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_list);

        Button navigate_btn = findViewById(R.id.button);
        ImageView rImage = findViewById(R.id.imgPrueba);
        LL = findViewById(R.id.LinL);
        i = 0;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference getImage = databaseReference.child("canchas").child("quito").child("1").child("fields").child("0").child("img");

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // getting a DataSnapshot for the location at the specified
                // relative path and getting in the link variable
                String link = dataSnapshot.getValue(String.class);

                // loading that data into rImage
                // variable which is ImageView
                Picasso.get().load(link).into(rImage);
            }

            // this will called when any problem
            // occurs in getting data
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(SF_list.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });

        navigate_btn.setOnClickListener(view -> {
            add(nombres[i]);
            i++;
        });
    }

    private void add(String n){
        View v = getLayoutInflater().inflate(R.layout.card,null);
        Button b = v.findViewById(R.id.button3);
        b.setText(n);
        LL.addView(v);
    }

}