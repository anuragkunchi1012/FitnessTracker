package com.example.fitnesstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WorkoutAdapter extends ArrayAdapter {
    ArrayList<WorkoutModelClass> arrayList = new ArrayList<>();
    Context context;
    int size=0;


    public WorkoutAdapter(@NonNull Context context, int resource, ArrayList<WorkoutModelClass> object) {
        super(context, resource,object);
        this.context = context;
        this.arrayList = object;
    }
    public WorkoutAdapter(@NonNull Context context, int resource, ArrayList<WorkoutModelClass> object, int size) {
        super(context, resource,object);
        this.context = context;
        this.arrayList = object;
        this.size= size;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.workoutlistdesign,null);

        TextView wName = view.findViewById(R.id.wname);
        TextView wTime = view.findViewById(R.id.resttime);
        TextView wDates = view.findViewById(R.id.dates);

        wName.setText(arrayList.get(position).getWname());
        wTime.setText(arrayList.get(position).getRestTime());
        wDates.setText(arrayList.get(position).getWDate());



        return view;
    }

    @Override
    public int getCount()
    {
        if (size==0) {
            return super.getCount();
        }
        else {
            return 5;
        }
    }
}
