package com.pmprogramms.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.pmprogramms.timer.fragments.ClockFragment;
import com.pmprogramms.timer.fragments.SettingsFragment;
import com.pmprogramms.timer.fragments.StopwatchFragment;
import com.pmprogramms.timer.fragments.TimerFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout framelayout;
    private ImageButton clockButton;
    private ImageButton timerButton;
    private ImageButton stopWatchButton;
    private ImageButton settingsButton;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        framelayout = findViewById(R.id.container);
        clockButton = findViewById(R.id.clock_button);
        timerButton = findViewById(R.id.timer_button);
        stopWatchButton = findViewById(R.id.stopwatch_button);
        settingsButton = findViewById(R.id.settings_button);

        clockButton.setOnClickListener(this);
        timerButton.setOnClickListener(this);
        stopWatchButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);

        initFragment(new ClockFragment());
    }

    private void initFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.clock_button) {
            initFragment(new ClockFragment());
        } else if (vId == R.id.timer_button) {
            initFragment(new TimerFragment());
        } else if (vId == R.id.stopwatch_button) {
            initFragment(new StopwatchFragment());
        } else if (vId == R.id.settings_button) {
            initFragment(new SettingsFragment());
        }
    }
}