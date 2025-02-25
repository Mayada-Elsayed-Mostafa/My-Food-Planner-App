package com.example.myfoodplannerapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    boolean isLoggedIn;
    SharedPreferences preferences;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabAdd;
    CoordinatorLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("userData", MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        layout = findViewById(R.id.cor_layout);
        fabAdd = findViewById(R.id.addFragment);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);

        fabAdd.setOnClickListener(view -> {
            navController.navigate(R.id.addFragment);
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment || destination.getId() == R.id.searchFragment || destination.getId() == R.id.calenderFragment || destination.getId() == R.id.favoriteFragment) {
                toolbar.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
            } else {
                toolbar.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (isLoggedIn) {
            if (id == R.id.profile_icon) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.action_homeFragment_to_profileFragment);
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Login Required")
                    .setMessage("Sorry, you must log in first to access your profile.")
                    .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                            navController.navigate(R.id.action_homeFragment_to_loginFragment);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        return true;
    }
}
