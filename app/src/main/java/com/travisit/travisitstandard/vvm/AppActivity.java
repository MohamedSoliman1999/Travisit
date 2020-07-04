package com.travisit.travisitstandard.vvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.databinding.ActivityAppBinding;
import com.travisit.travisitstandard.vvm.observer.BottomNavigationControl;
import com.travisit.travisitstandard.vvm.observer.IOnBackPressed;

public class AppActivity extends AppCompatActivity implements BottomNavigationControl {
    private ActivityAppBinding binding;
    public static Boolean isTourGuide = false;
    NavHostFragment navHostFragment;
    protected IOnBackPressed onBackPressedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.act_app_nav_host_fragment);
        NavigationUI.setupWithNavController(binding.activityAppBottomNavBar, navHostFragment.getNavController());
        handleUserInteractions();
    }
    /**
     * This function controls the visibility of the bottom navigation bar
     *
     */
    @Override
    public void changeBottomNavVisibility(Integer isVisible) {
        binding.activityAppBottomNavBar.setVisibility(isVisible);
        if(isTourGuide){
            binding.activityAppFabAdd.setVisibility(isVisible);
        } else {
            binding.activityAppFabAdd.setVisibility(View.GONE);
        }
    }
    public void setOnBackPressedListener(IOnBackPressed onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.onBackPressed();
        } else
            super.onBackPressed();
    }
    private void handleUserInteractions() {
        binding.activityAppFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDestination destination = getFragment();

                if ("fragment_home".equals(destination.getLabel())) {
                    //navHostFragment.getNavController().navigate(R.id.action_to_add_offer);
                } else if ("fragment_explore".equals(destination.getLabel())) {
                    //navHostFragment.getNavController().navigate(R.id.action_to_add_branch);
                }  else if ("fragment_chat_list".equals(destination.getLabel())) {
                   // navHostFragment.getNavController().navigate(R.id.action_to_add_branch);
                } else if ("fragment_notifications".equals(destination.getLabel())) {
                } else if ("fragment_account".equals(destination.getLabel())) {
                }
            }
        });
    }
    public NavDestination getFragment(){
        return NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment().getFragmentManager().getFragments().get(0)).getCurrentDestination();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
