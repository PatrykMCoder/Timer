package com.pmprogramms.timer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pmprogramms.timer.R;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClockFragment extends Fragment {

    private TextView clockView;
    private TextView dateView;

    private Handler handler;
    private Runnable runnable;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_clock, container, false);
        clockView = rootView.findViewById(R.id.clock_text);
        dateView = rootView.findViewById(R.id.date_text);

        handler = new Handler(context.getMainLooper());
        runnable = this::updateUI;

        handler.postDelayed(runnable, 1000);
        return rootView;
    }

    @SuppressLint("SimpleDateFormat")
    private void updateUI() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        clockView.setText(simpleDateFormat.format(calendar.getTime()));
        dateView.setText(dateFormat.format(date));
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
