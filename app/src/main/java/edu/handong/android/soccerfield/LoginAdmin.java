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

    FirebaseAuth mAuth;
    EditText userName;
    EditText pswd;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        mAuth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.username_text);
        pswd = findViewById(R.id.password_text);
        login = findViewById(R.id.Sign_in);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
                //Toast.makeText(LoginAdmin.this, userName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Login (){
        String email = userName.getText().toString().trim();
        String psw = pswd.getText().toString();

        if (TextUtils.isEmpty(email)){
            userName.setError("Email shouldn't be empty");
            userName.requestFocus();
        } else if (TextUtils.isEmpty(psw)){
            pswd.setError("Email shouldn't be empty");
            pswd.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginAdmin.this, "Welcome "+email, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginAdmin.this, "Login error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}