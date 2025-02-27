package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class ProfileFragment extends Fragment {

    SharedPreferences preferences;
    Button logout, seePlan, seeFav;
    TextView userName;
    Boolean isLoggedIn;
    ImageView loginFirst;

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
        isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        logout = view.findViewById(R.id.btn_logout);
        seePlan = view.findViewById(R.id.btn_see_plan);
        seeFav = view.findViewById(R.id.btn_see_fav);
        userName = view.findViewById(R.id.tv_userNameProfile);
        loginFirst = view.findViewById(R.id.no_connect_iv);

        if (!isLoggedIn) {
            loginFirst.setVisibility(View.VISIBLE);
        } else {
            loginFirst.setVisibility(View.GONE);
        }

        userName.setText(preferences.getString("name", ""));

        logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("email");
            editor.remove("isLoggedIn");
            editor.apply();

            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_welcomeFragment);
        });

        seePlan.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_calenderFragment);
        });

        seeFav.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_favoriteFragment);
        });

        return view;
    }

}