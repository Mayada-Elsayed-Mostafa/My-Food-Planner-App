package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;

public class SplashFragment extends Fragment {

    SharedPreferences preferences;
    private static final int SPLASH_DELAY = 3000;

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

        TextView title = view.findViewById(R.id.title);

        title.post(() -> {
            LinearGradient gradient = new LinearGradient(0, 0, 0, title.getHeight(),
                    ContextCompat.getColor(getContext(), R.color.my_light_primary),
                    ContextCompat.getColor(getContext(), R.color.my_light_tertiary),
                    Shader.TileMode.CLAMP);

            title.getPaint().setShader(gradient);

            title.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            title.setShadowLayer(8, 0, 0, ContextCompat.getColor(getContext(), R.color.accent_yellow)); // تأثير توهج أصفر
        });

        preferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            new Handler().postDelayed(() ->
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment), SPLASH_DELAY);
        } else {
            new Handler().postDelayed(() ->
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_welcomeFragment), SPLASH_DELAY);
        }

        return view;
    }
}
