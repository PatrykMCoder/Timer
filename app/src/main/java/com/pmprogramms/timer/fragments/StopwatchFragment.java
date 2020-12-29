package com.pmprogramms.timer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.pmprogramms.timer.R;

import java.util.ArrayList;

public class StopwatchFragment extends Fragment {
    private TextView timeTV, lapsTV;
    private Button actionBtn, resetBtn, lapBtn;

    private Handler handler;
    private Runnable runnable;
    private int seconds = 0, minutes = 0, hours = 0;
    private String totalString = "00:00:00";
    private String lapsString = "";

    private boolean running;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        createStopWatchThread();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        timeTV = view.findViewById(R.id.time_text);
        lapsTV = view.findViewById(R.id.laps_text);
        actionBtn = view.findViewById(R.id.action_button);
        resetBtn = view.findViewById(R.id.reset_button);
        lapBtn = view.findViewById(R.id.lap_button);

        lapBtn.setOnClickListener(v -> {
            createLap();
        });

        resetBtn.setOnClickListener(v -> {
            running = false;
            handler.removeCallbacks(runnable);
            seconds = minutes = hours = 0;
            lapsString = "";
            lapsTV.setText(lapsString);
            updateUI();
        });

        actionBtn.setOnClickListener(v -> {
            running = !running;
            handler.postDelayed(runnable, 1000);
        });

        return view;
    }

    private void createStopWatchThread() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (running) updateTime();
                else handler.removeCallbacks(this);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void updateTime() {
        seconds++;

        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }

        if (minutes == 60) {
            minutes = 0;
            hours++;
        }

        updateUI();
        handler.postDelayed(runnable, 1000);
    }

    private void updateUI() {
        String secondsString, minutesString, hoursString;

        if (seconds < 10)
            secondsString = String.format("0%s", seconds);
        else
            secondsString = String.format("%s", seconds);

        if (minutes < 10)
            minutesString = String.format("0%s", minutes);
        else
            minutesString = String.format("%s", minutes);

        if (hours < 10)
            hoursString = String.format("0%s", hours);
        else
            hoursString = String.format("%s", hours);

        totalString = String.format("%s:%s:%s", hoursString, minutesString, secondsString);

        actionBtn.setText(running ? "stop" : "start");
        timeTV.setText(totalString);
    }

    private void createLap() {
        //todo <- make it better pls
        lapsString += totalString + "\n\n";
        lapsTV.setText(lapsString);
    }
}
