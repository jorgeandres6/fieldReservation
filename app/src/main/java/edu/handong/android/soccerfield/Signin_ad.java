package edu.handong.android.soccerfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin_ad extends AppCompatActivity {

    //VARIABLES DECLARATION
    TextView etEmail;
    TextView etRepeatEmail;
    TextView etPsw;
    TextView etRepeatPsw;
    Button btnRegister;
    FirebaseAuth mAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_ad);

        //REFERENCE TO UI ELEMENTS
        etEmail = findViewById(R.id.etEmailSI);
        etRepeatEmail = findViewById(R.id.etRepeatEmailSI);
        etPsw = findViewById(R.id.etPswSI);
        etRepeatPsw = findViewById(R.id.etRepeatPswSI);
        btnRegister = findViewById(R.id.btnRegisterSI);

        //REFERENCE TO FIREBASE AUTHENTICATION
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            } //CREATE A NEW USER METHOD
        });
    }

    private void createUser ()  {

        //GET DATA FROM UI ELEMENTS
        String email = etEmail.getText().toString(); //EMAIL
        String psw = etPsw.getText().toString(); //PASSWORD

        if (TextUtils.isEmpty(email)){ //IF EMAIL EDIT TEXT IS EMPTY
            etEmail.setError("Email can't be empty"); //DISPLAY AN ERROR MESSAGE
            etEmail.requestFocus();
        }else if (TextUtils.isEmpty(psw)){ //IF PASSWORD EDIT TEXT IS EMPTY
            etPsw.setError("Password can't be empty"); //DISPLAY AN ERROR MESSAGE
            etPsw.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //USING FIREBASE METHOD TO CREATE A USER BASED ON EMAIL AND PASSWORD
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Signin_ad.this,"Registration success", Toast.LENGTH_SHORT).show(); //SHOW SUCCESSFUL REGISTRATION MESSAGE
                        startActivity(new Intent(Signin_ad.this, MainActivity.class)); //LAUNCH MAIN MENU ACTIVITY
                        finish(); //FINISH THIS ACTIVITY
                    }else  {
                        Toast.makeText(Signin_ad.this,"Registration error: "+task.getException().getMessage(),Toast.LENGTH_LONG); //ERROR MESSAGE
                    }
                }
            });
        }
    }
}