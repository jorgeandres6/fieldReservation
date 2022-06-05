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

        etEmail = findViewById(R.id.etEmailSI);
        etRepeatEmail = findViewById(R.id.etRepeatEmailSI);
        etPsw = findViewById(R.id.etPswSI);
        etRepeatPsw = findViewById(R.id.etRepeatPswSI);
        btnRegister = findViewById(R.id.btnRegisterSI);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser ()  {
        String email = etEmail.getText().toString();
        String psw = etPsw.getText().toString();

        if (TextUtils.isEmpty(email)){
            etEmail.setError("Email can't be empty");
            etEmail.requestFocus();
        }else if (TextUtils.isEmpty(psw)){
            etPsw.setError("Password can't be empty");
            etPsw.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Signin_ad.this,"Registration succes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signin_ad.this, MainActivity.class));
                    }else  {
                        Toast.makeText(Signin_ad.this,"Registration error: "+task.getException().getMessage(),Toast.LENGTH_LONG);
                    }
                }
            });
        }
    }
}