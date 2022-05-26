package edu.handong.android.soccerfield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
/**
 *this class shows the information for all the soccer fields
 */

public class SoccerFieldInfo extends AppCompatActivity {

    String opening;
    String close;
    String address;
    String name;
    String img;
    int cost,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_field_info);

        opening="";
        close="";
        address="";
        name="";
        img="";
        cost=0;
        total=0;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            opening = extras.getString("opening");
            close = extras.getString("close");
            address = extras.getString("address");
            name = extras.getString("name");
            img = extras.getString("img");
            cost = extras.getInt("cost");
            total = cost;
            //The key argument here must match that used in the other activity
        }

        TextView tvName = findViewById(R.id.tvFnameI);
        TextView tvAddress = findViewById(R.id.tvFaddressI);
        TextView tvCost = findViewById(R.id.tvFcostI);
        TextView tvTotal = findViewById(R.id.tvTotalI);
        ImageView Fimg = findViewById(R.id.ivFimgI);
        Button btnReserve = findViewById(R.id.btnMakeReservation);

        tvName.setText(name);
        tvAddress.setText(address);
        tvCost.setText(cost+" USD/hour");
        tvTotal.setText("Total to pay: "+total+" USD");

        Picasso.get().load(img).into(Fimg);

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ReserveConfirm.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });
    }
}