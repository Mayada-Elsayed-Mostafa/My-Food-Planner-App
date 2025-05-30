package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodplannerapplication.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    SharedPreferences preferences;
    Boolean isLoggedIn;
    private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = requireActivity().getSharedPreferences("userData", MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            binding.noConnectIv.setVisibility(View.VISIBLE);
        } else {
            binding.noConnectIv.setVisibility(View.GONE);
        }

        binding.tvUserNameProfile.setText(preferences.getString("name", ""));

        binding.btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("email");
            editor.remove("isLoggedIn");
            editor.apply();

            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_welcomeFragment);
        });

        binding.btnSeePlan.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_calenderFragment);
        });

        binding.btnSeeFav.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_favoriteFragment);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}