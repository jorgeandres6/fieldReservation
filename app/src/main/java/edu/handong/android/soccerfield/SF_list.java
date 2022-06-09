package edu.handong.android.soccerfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SF_list extends AppCompatActivity {

    //DECLARING VARIABLES
    LinearLayout LL;
    List<Fields> Afields = new ArrayList<Fields>();
    int i, k;
    DatabaseReference getData;
    String city = "";
    String prueba;
    ScrollView SVFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_list);

        //INITIALIZE VARIABLES
        i = 0;
        k = 0;
        prueba = "";

        //RETRIEVE DATA FROM PREVIOUS ACTIVITY
        Bundle extras = getIntent().getExtras();
        if (extras != null) { //IF THERE'S ANY DATA
            city = extras.getString("city"); //RETRIEVE PREVIOUS SELECTED CITY NAME
        }

        //REFERENCE TO FIREBASE RT-DB
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        getData = databaseReference.child("canchas").child(city); //REFERENCE TO SOCCER FIELDS' DATA BRANCH

        //REFERENCE TO UI ELEMENTS
        SVFields = findViewById(R.id.SVFields);
        LL = findViewById(R.id.LinL);


        getData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //LOOP TROUGH CHILD ELEMENTS IN SOCCER FIELDS' DATA BRANCH
                    Fields fields = snapshot.getValue(Fields.class); //GET A CHILD ELEMENT
                    Afields.add(fields); //ADD THE CHILD ELEMENT TO THE ARRAY LIST FOR FURTHER ITERATION
                }
               for (int j=0; j<Afields.size(); j++){ //LOOP TROUGH THE ARRAY LIST AND ADD ELEMENTS TO UI
                    add(j);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SF_list.this, "Error Loading Image", Toast.LENGTH_SHORT).show(); //ERROR MESSAGE ON IMAGE RENDERING
            }
        });

    }

    private void add(int i){ //METHOD FOR ADDING SOCCER FIELDS' INFO CARD INTO THE UI

        View v = getLayoutInflater().inflate(R.layout.card,null); //REFERENCE TO CARD RESOURCE
        v.setTag(i); //SETTING AN IDENTIFICATION TO EACH CARD

        //REFERENCE TO UI ELEMENTS
        ImageView img = v.findViewById(R.id.imgCard); //SOCCER FIELD IMAGE
        TextView TV = v.findViewById(R.id.TVname); //SOCCER FIELD NAME

        //SETTING VALUES TO UI ELEMENTS
        TV.setText(Afields.get(i).getNombre()); //SOCCER FIELD NAME
        Picasso.get().load(Afields.get(i).getFoto()).into(img); //RENDER IMAGE

        //ADD CARD TO THE UI
        LL.addView(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SoccerFieldInfo.class); //LAUNCH SOCCER FIELD'S INFO ACTIVITY
                //TRANSFER DATA TO NEXT ACTIVITY
                intent.putExtra("name", Afields.get(Integer.parseInt(v.getTag().toString())).getNombre()); //FIELD NAME
                intent.putExtra("close", Afields.get(Integer.parseInt(v.getTag().toString())).getCierre()); //FIELD CLOSING HOUR
                intent.putExtra("img", Afields.get(Integer.parseInt(v.getTag().toString())).getFoto()); //FIELD IMAGE
                intent.putExtra("opening", Afields.get(Integer.parseInt(v.getTag().toString())).getApertura()); //FIELD OPENING HOUR
                intent.putExtra("address", Afields.get(Integer.parseInt(v.getTag().toString())).getDireccion()); //FIELD ADDRESS
                intent.putExtra("cost", Afields.get(Integer.parseInt(v.getTag().toString())).getCost()); //FIELD COST PER HOUR
                intent.putExtra("id", Afields.get(Integer.parseInt(v.getTag().toString())).getId()); //FIELD ID
                startActivity(intent);
            }
        });
    }

}

class Fields { //CLASS FOR RETRIEVING DATA FROM FIREBASE RT-DB FIELDS' INFO BRANCH

    //DECLARE VARIABLES ACCORDING TO FIREBASE RT-DB FIELDS NAME
    private int apertura;
    private int cierre;
    private String direccion;
    private String nombre;
    private String id;
    private String foto;
    private int cost;

    //GETTERS AND SETTERS
    public int getApertura() {
        return apertura;
    }

    public void setApertura(int apertura) {
        this.apertura = apertura;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}