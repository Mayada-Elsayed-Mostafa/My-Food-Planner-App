package com.example.myfoodplannerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText enterEmail;
    private EditText enterPassword;
    private TextView forgotPassword;
    private Button login;
    private TextView createAnAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        enterEmail = findViewById(R.id.et_enterEmail);
        enterPassword = findViewById(R.id.et_enterPassword);
        forgotPassword = findViewById(R.id.tv_forgotPassword);
        login = findViewById(R.id.btn_login);
        createAnAccount = findViewById(R.id.tv_createAccount);


        login.setOnClickListener(view -> {

            String email = enterEmail.getText().toString();
            String password = enterPassword.getText().toString();

            if (email.isEmpty()) {
                enterEmail.setError("Email is required!");
                enterEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                enterPassword.setError("Password is required!");
                enterPassword.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("loginTag", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                Log.w("loginTag", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        createAnAccount.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}