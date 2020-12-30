package com.pmprogramms.timer.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pmprogramms.timer.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimerFragment extends Fragment {

    private EditText timeET; //todo -> use other component
    private TextView currentTimeTV;
    private Button actionBtn, resetBtn;

    private CountDownTimer countDownTimer;
    private long currentCounterTime = 0;
    private boolean running = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        timeET = view.findViewById(R.id.time_select);
        currentTimeTV = view.findViewById(R.id.current_time_text);
        actionBtn = view.findViewById(R.id.action_button);
        resetBtn = view.findViewById(R.id.reset_button);

        actionBtn.setOnClickListener(v -> {
            running = !running;

            if (running)
                createCounter(currentCounterTime != 0 ? currentCounterTime : (currentCounterTime = getTimeFromField(timeET.getText().toString())));
            else {
                countDownTimer.cancel();
                updateUI();
            }
        });

        resetBtn.setOnClickListener(v -> {
            countDownTimer.cancel();
            currentCounterTime = 0;
            running = false;
            updateUI();
        });
        return view;
    }

    private void createCounter(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentCounterTime = millisUntilFinished;
                updateUI();
            }

            @Override
            public void onFinish() {
                currentCounterTime = 0;
                running = false;
                updateUI();
            }
        }.start();
    }

    private void updateUI() {
        Date date = new Date(currentCounterTime);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(date);

        currentTimeTV.setText(dateFormatted);
        actionBtn.setText(running ? "Stop" : "Start");
    }

    private long getTimeFromField(String time) {
        String[] timeSplit = time.split(":");
        long seconds = Long.parseLong(timeSplit[2]);
        long minutes = Long.parseLong(timeSplit[1]);
        long hours = Long.parseLong(timeSplit[0]);

        return (seconds * 1000) + (minutes * 60 * 1000) + (hours * 60 * 60 * 1000);
    }
}
