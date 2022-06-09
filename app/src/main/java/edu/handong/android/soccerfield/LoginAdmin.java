package edu.handong.android.soccerfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAdmin extends AppCompatActivity {

    //DECLARE VARIABLES

    FirebaseAuth mAuth;
    EditText userName;
    EditText pswd;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        mAuth = FirebaseAuth.getInstance(); //GET INSTANCE FROM THE REALTIME DB FIREBASE

        //REFER TO UI ELEMENTS
        userName = findViewById(R.id.username_text);
        pswd = findViewById(R.id.password_text);
        login = findViewById(R.id.Sign_in);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login(); //CALL LOGIN METHOD
            }
        });
    }

    private void Login (){
        String email = userName.getText().toString().trim(); //GET EMAIL WITH NO BLANK SPACES FROM THE EDIT TEXT
        String psw = pswd.getText().toString(); //GET PASSWORD FROM THE EDIT TEXT

        if (TextUtils.isEmpty(email)){ //IF EMAIL EDIT TEXT IS EMPTY REQUEST FOR COMPLIANCE
            userName.setError("Email shouldn't be empty");
            userName.requestFocus();
        } else if (TextUtils.isEmpty(psw)){ //IF PASSWORD EDIT TEXT IS EMPTY REQUEST FOR COMPLIANCE
            pswd.setError("Email shouldn't be empty");
            pswd.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //USE FIREBASE METHOD TO SIGN IN WITH EMAIL AND PASSWORD
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){ //IF EMAIL AND PASSWORD LOGIN IS SUCCESSFUL
                        Toast.makeText(LoginAdmin.this, "Welcome "+email, Toast.LENGTH_SHORT).show(); //DISPLAY WELCOME MESSAGE
                        finish(); //END ACTIVITY TO REFRESH T
                    }else {
                        Toast.makeText(LoginAdmin.this, "Login error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show(); //LOGIN ERROR MESSAGE
                    }
                }
            });
        }
    }
}