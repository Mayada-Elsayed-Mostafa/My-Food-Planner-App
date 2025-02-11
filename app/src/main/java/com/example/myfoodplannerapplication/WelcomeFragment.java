package com.example.myfoodplannerapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class WelcomeFragment extends Fragment {

    Button signUp;
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

        signUp = view.findViewById(R.id.btn_signup);
        logIn = view.findViewById(R.id.tv_login);

        signUp.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signupFragment)
        );

        logIn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment)
        );

        return view;
    }
}