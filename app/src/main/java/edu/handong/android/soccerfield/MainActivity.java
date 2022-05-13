package edu.handong.android.soccerfield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button navigate_btn = findViewById(R.id.CheckField);
        navigate_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this,SF_list.class);
            startActivity(intent);
        });

    }


}