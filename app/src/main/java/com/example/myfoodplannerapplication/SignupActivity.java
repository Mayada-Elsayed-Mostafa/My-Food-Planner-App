package com.example.myfoodplannerapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;


public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser user;

    TextView name;
    TextView email;
    TextView phone;
    TextView password;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_phone);
        password = findViewById(R.id.et_password);
        signUp = findViewById(R.id.btn_signUp);

        signUp.setOnClickListener(view -> {

            user = FirebaseAuth.getInstance().getCurrentUser();

            String emailEntered = email.getText().toString();
            String passwordEntered = password.getText().toString();
            String nameEntered = name.getText().toString();

            mAuth.fetchSignInMethodsForEmail(emailEntered)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.isSuccessful()) {
                                boolean isEmailExists = !task.getResult().getSignInMethods().isEmpty();
                                if (isEmailExists) {
                                    email.setError("This Email Exits Already");
                                    email.requestFocus();
                                }
                            }
                        }
                    });

            if (passwordEntered.length() < 6) {
                Toast.makeText(SignupActivity.this, "Sorry, Enter at least 6 characters for password.",
                        Toast.LENGTH_SHORT).show();

            } else {
                mAuth.createUserWithEmailAndPassword(emailEntered, passwordEntered)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("signupTag", "createUserWithEmail:success");
                                    user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nameEntered)
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("signupTag", user.getDisplayName());
                                                    }
                                                }
                                            });
                                } else {

                                    Log.w("signupTag", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });


    }
}