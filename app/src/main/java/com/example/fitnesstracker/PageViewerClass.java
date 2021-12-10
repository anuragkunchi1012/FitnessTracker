package com.example.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageViewerClass extends FragmentStateAdapter {
    public static final String[] tabs = {"Workout","BMI Calculator", "History"};
    public PageViewerClass(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new WorkoutFragment();
            case 1: return new BmiCalculator();
            case 2: return new HistoryFragment();
        }

        return new WorkoutFragment();
    }

    @Override
    public int getItemCount() {
        return tabs.length;
    }
}
