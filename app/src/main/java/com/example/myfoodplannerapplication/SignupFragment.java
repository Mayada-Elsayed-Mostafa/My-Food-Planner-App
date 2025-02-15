package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText phone;
    private TextInputEditText password;
    private Button signUp;
    private TextView login;
    SharedPreferences preferences;

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
        login = view.findViewById(R.id.tv_login);

        preferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);

        signUp.setOnClickListener(view1 -> {
            String emailEntered = email.getText().toString();
            String passwordEntered = password.getText().toString();
            String nameEntered = name.getText().toString();

            if (passwordEntered.length() < 6) {
                Toast.makeText(requireContext(), "Enter at least 6 characters for password.", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.fetchSignInMethodsForEmail(emailEntered).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    boolean isEmailExists = !task.getResult().getSignInMethods().isEmpty();
                    if (isEmailExists) {
                        email.setError("This Email Exists Already");
                        email.requestFocus();
                        return;
                    }
                    registerUser(emailEntered, passwordEntered, nameEntered, view);
                }
            });
        });

        login.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);
        });

        return view;
    }

    private void registerUser(String email, String password, String name, View view) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d("signupTag", "User created successfully");

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        user = mAuth.getCurrentUser();
                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Log.d("signupTag", "User profile updated: " + user.getDisplayName());
                                }
                            });
                        }

                        Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_homeFragment);
                    } else {
                        Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}