package com.example.myfoodplannerapplication;

import android.os.Bundle;
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

public class WelcomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    Button signUp;
    Button skip;
    TextView logIn;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        mAuth = FirebaseAuth.getInstance();

        signUp = view.findViewById(R.id.btn_signup);
        skip = view.findViewById(R.id.btn_skip);
        logIn = view.findViewById(R.id.tv_login);

        signUp.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signupFragment)
        );

        logIn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment)
        );

        skip.setOnClickListener(v -> {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Navigation.findNavController(requireView()).navigate(R.id.action_welcomeFragment_to_homeFragment);
                            } else {
                                Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        return view;
    }
}