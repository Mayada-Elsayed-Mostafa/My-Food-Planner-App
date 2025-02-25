package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
    private Button signUp;
    private Button skip;
    private TextView logIn;
    private SharedPreferences preferences;

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

        preferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        signUp = view.findViewById(R.id.btn_signup);
        skip = view.findViewById(R.id.btn_skip);
        logIn = view.findViewById(R.id.tv_login);

        signUp.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signupFragment);
        });

        logIn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment);
        });

        skip.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();
            mAuth.signInAnonymously()
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_homeFragment);
                            } else {
                                Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        return view;
    }
}
