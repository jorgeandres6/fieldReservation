package edu.handong.android.soccerfield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Update_sf_info extends AppCompatActivity {

    Button btnCancel, btnAdd;
    EditText etName, etAddress, etOHour, etCHour, etPrice;
    String[] cities = {"Quito","Ibarra"};
    String selectedCity = "Quito";
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sf_info);

        btnCancel = findViewById(R.id.btnCancelAU);
        btnAdd = findViewById(R.id.btnAddFieldAU);
        etName = findViewById(R.id.etFieldNameUI);
        etAddress = findViewById(R.id.etAddressUI);
        etOHour = findViewById(R.id.etOHAUI);
        etCHour = findViewById(R.id.etCHAI);
        etPrice = findViewById(R.id.etCostAU);
        mAuth = FirebaseAuth.getInstance();

        uuid = mAuth.getCurrentUser().getUid();

        autoCompleteText = findViewById(R.id.AUUI);
        adapterItems = new ArrayAdapter<String>(this,R.layout.items,cities);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        autoCompleteText.setAdapter(adapterItems);

        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),"City: "+city,Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("canchas").child(selectedCity.toLowerCase());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long currentTime = System.currentTimeMillis()/1000;
                String timestamp = currentTime.toString();
                switch (selectedCity) {
                    case "Quito":
                        //Toast.makeText(Update_sf_info.this, "UIO", Toast.LENGTH_SHORT).show();
                        databaseReference.child(uuid+timestamp).setValue(new CreateField(etName.getText().toString(),etAddress.getText().toString(),"UIO"+timestamp,Integer.valueOf(etOHour.getText().toString()),Integer.valueOf(etCHour.getText().toString()),Integer.valueOf(etPrice.getText().toString())));
                        break;

                    case "Ibarra":
                        Toast.makeText(Update_sf_info.this, "IBR", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        //Toast.makeText(Update_sf_info.this, "UIO", Toast.LENGTH_SHORT).show();
                        databaseReference.setValue(new CreateField(etName.getText().toString(),etAddress.getText().toString(),"UIO"+timestamp,Integer.valueOf(etOHour.getText().toString()),Integer.valueOf(etCHour.getText().toString()),Integer.valueOf(etPrice.getText().toString())));
                }
            }
        });
    }
}

class CreateField {
    public int apertura;
    public int cierre;
    public String direccion;
    public String nombre;
    public String id;
    public String foto;
    public int cost;

    public CreateField (String nombre, String direccion, String id, int apertura, int cierre, int total){
        this.nombre = nombre;
        this.id = id;
        this.direccion = direccion;
        this.cost = total;
        this.foto = "https://firebasestorage.googleapis.com/v0/b/canchas-e446d.appspot.com/o/Field2.jpg?alt=media&token=870d063d-8dfb-4890-a947-394c429760d7";
        this.apertura = apertura;
        this.cierre = cierre;
    }
}