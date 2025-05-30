package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodplannerapplication.databinding.FragmentSignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupFragment extends Fragment {

    SharedPreferences preferences;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FragmentSignupBinding binding;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();


        preferences = requireActivity().getSharedPreferences("userData", MODE_PRIVATE);

        binding.btnSignUp.setOnClickListener(view1 -> {
            String emailEntered = binding.etEmailVal.getText().toString();
            String passwordEntered = binding.etPasswordVal.getText().toString();
            String nameEntered = binding.etNameVal.getText().toString();

            if (passwordEntered.length() < 6) {
                Toast.makeText(requireContext(), "Enter at least 6 characters for password.", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.fetchSignInMethodsForEmail(emailEntered).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    boolean isEmailExists = !task.getResult().getSignInMethods().isEmpty();
                    if (isEmailExists) {
                        binding.etEmailVal.setError("This Email Exists Already");
                        binding.etEmailVal.requestFocus();
                        return;
                    }
                    registerUser(emailEntered, passwordEntered, nameEntered, view);
                }
            });
        });

        binding.tvLogin.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);
        });

    }

    private void registerUser(String email, String password, String name, View view) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", name);
                editor.putString("email", email);
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                user = mAuth.getCurrentUser();
                if (user != null) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}