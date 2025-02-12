package com.example.myfoodplannerapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView password;
    private Button signUp;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        mAuth = FirebaseAuth.getInstance();

        name = view.findViewById(R.id.et_name_val);
        email = view.findViewById(R.id.et_email_val);
        phone = view.findViewById(R.id.et_phone_val);
        password = view.findViewById(R.id.et_password_val);
        signUp = view.findViewById(R.id.btn_signUp);

        signUp.setOnClickListener(view1 -> {
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
                                    email.setError("This Email Exists Already");
                                    email.requestFocus();
                                }
                            }
                        }
                    });

            if (passwordEntered.length() < 6) {
                Toast.makeText(requireContext(), "Sorry, Enter at least 6 characters for password.",
                        Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(emailEntered, passwordEntered)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
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
                                    Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_homeFragment);
                                } else {
                                    Toast.makeText(requireContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return view;
    }
}
