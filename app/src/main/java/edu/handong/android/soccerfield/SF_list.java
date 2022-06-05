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

public class SF_list extends AppCompatActivity {

    LinearLayout LL;
    Fields[] Afields = new Fields[10];
    int i, k;
    DatabaseReference getData;
    String city = "";
    String prueba;
    ScrollView SVFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_list);

        LL = findViewById(R.id.LinL);
        i = 0;
        k = 0;
        prueba = "";
        SVFields = findViewById(R.id.SVFields);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            city = extras.getString("city");
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        //getImage = databaseReference.child("canchas").child("quito").child("1").child("fields").child("0").child("img");
        getData = databaseReference.child("canchas").child(city);

        getData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Fields fields = snapshot.getValue(Fields.class);
                    fields.setFoto(snapshot.child("foto").getValue().toString());
                    Afields[i] = fields;
                    i++;
                    k++;
                }

                i=0;

               for (int j=0; j<k; j++){
                    add();
                }

            }

            // this will called when any problem
            // occurs in getting data
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(SF_list.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void add(){

        View v = getLayoutInflater().inflate(R.layout.card,null);
        v.setTag(i);
        ImageView img = v.findViewById(R.id.imgCard);
        TextView TV = v.findViewById(R.id.TVname);

        TV.setText(Afields[i].getNombre());
        Picasso.get().load(Afields[i].getFoto()).into(img);

        LL.addView(v);
        i++;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),Afields[Integer.parseInt(v.getTag().toString())].getNombre(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),SoccerFieldInfo.class);
                intent.putExtra("name", Afields[Integer.parseInt(v.getTag().toString())].getNombre());
                intent.putExtra("close", Afields[Integer.parseInt(v.getTag().toString())].getCierre());
                intent.putExtra("img", Afields[Integer.parseInt(v.getTag().toString())].getFoto());
                intent.putExtra("opening", Afields[Integer.parseInt(v.getTag().toString())].getApertura());
                intent.putExtra("address", Afields[Integer.parseInt(v.getTag().toString())].getDireccion());
                intent.putExtra("cost", Afields[Integer.parseInt(v.getTag().toString())].getCost());
                intent.putExtra("id", Afields[Integer.parseInt(v.getTag().toString())].getId());
                startActivity(intent);
            }
        });
    }

}

class Fields {
    private int apertura;
    private int cierre;
    private String direccion;
    private String nombre;
    private String id;
    private String foto;
    private int cost;

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