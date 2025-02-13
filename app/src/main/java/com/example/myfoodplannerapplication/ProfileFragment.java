package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class ProfileFragment extends Fragment {

    SharedPreferences preferences;
    Button logout;
    TextView userName;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        preferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);
        logout = view.findViewById(R.id.btn_logout);
        userName = view.findViewById(R.id.tv_userNameProfile);

        userName.setText(preferences.getString("name",""));

        logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("email");
            editor.remove("isLoggedIn");
            editor.apply();

            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_welcomeFragment);

        });
        return view;
    }
}