package com.example.myfoodplannerapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;

public class SplashFragment extends Fragment {

    private static final int SPLASH_DELAY = 5000;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottie_splash);
        lottieAnimationView.setAnimation(R.raw.splash_animation);
        lottieAnimationView.playAnimation();

        new Handler().postDelayed(() ->
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_welcomeFragment), SPLASH_DELAY);

        return view;
    }
}
