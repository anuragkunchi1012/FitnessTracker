package com.example.fitnesstracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {
    ProgressBar progressBar;
    TextView progresstext,exitBtn,textView;
    EditText times, workoutName;
    Button start;
    double i = 1, totaltime = 0;
    String workName, Stime, currentDate ;
    boolean alarmCalled = true;
    RelativeLayout layout, layout2;
    LinearLayout mainLayout;
    CheckBox checkBox;
    ListView listView;
    ArrayList<WorkoutModelClass> arrayList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        MyDatabaseClass databaseClass = new MyDatabaseClass(getContext());

        times = (EditText) view.findViewById(R.id.time);
        workoutName = (EditText) view.findViewById(R.id.workoutname);
        start = (Button) view.findViewById(R.id.starttime);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progresstext = (TextView) view.findViewById(R.id.progresstext);
        textView = (TextView) view.findViewById(R.id.textview11);
        exitBtn = (TextView) view.findViewById(R.id.exit);
        layout = (RelativeLayout) view.findViewById(R.id.prograsslayout);
        layout2 = (RelativeLayout) view.findViewById(R.id.relativelayout1);
        mainLayout = (LinearLayout) view.findViewById(R.id.mainlayout);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        listView = (ListView) view.findViewById(R.id.listview);

        final Handler handler = new Handler();


        showLastWorkout();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workName = workoutName.getText().toString();
                Stime = times.getText().toString();


                InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(times.getWindowToken(), 0);

                if (workName.trim().length()>0 && Stime.trim().length()>0)
                {
                    totaltime = Integer.parseInt(times.getText().toString());
                    if (checkBox.isChecked() == true) {

                        workoutName.setFocusable(false);
                        workoutName.setGravity(Gravity.CENTER_HORIZONTAL);
                        times.setVisibility(View.INVISIBLE);
                        start.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.INVISIBLE);


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // set the limitations for the numeric
                                // text under the progress bar


                                if (i <= totaltime) {

                                    double percentage = (i / Double.parseDouble(times.getText().toString())) * 100;
                                    int realpercentage = (int) percentage;
                                    System.out.println(realpercentage + " " + i + " " + totaltime);
                                    progressBar.setProgress(realpercentage);
                                    progresstext.setText("" + (int) i);
                                    i++;
                                    handler.postDelayed(this, 1000);
                                    if ((totaltime - i) <= 3 && alarmCalled == true) {
                                        alarmCalled = false;
                                        callAlarm();
                                    }


                                } else {
                                    alarmCalled = true;
                                    handler.removeCallbacks(this);
                                    progressBar.setProgress(0);
                                    totaltime = 0;
                                    i = 1;
                                    times.setVisibility(View.VISIBLE);
                                    start.setVisibility(View.VISIBLE);
                                    layout.setVisibility(View.INVISIBLE);
                                    layout2.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.VISIBLE);
                                    textView.setVisibility(View.VISIBLE);
                                    workoutName.setFocusableInTouchMode(true);
                                    workoutName.setGravity(Gravity.LEFT);
                                    workoutName.setText("");
                                    times.setText("");
                                    showLastWorkout();

                                }
                            }
                        }, 1000);

                        arrayList.add(new WorkoutModelClass(workName,Stime, currentDate));
                        currentDate = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(new Date());
                        databaseClass.insertIntoWorkout(workName,String.format("%.2f",totaltime),currentDate);
                    }
            }
                else {

                }

            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmCalled = true;
                progressBar.setProgress(0);
                totaltime = 0;
                i = 1;
                times.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                layout.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.VISIBLE);

            }
        });


        return view;
    }

    private void showLastWorkout() {
        MyDatabaseClass databaseClass = new MyDatabaseClass(getContext());
        arrayList = databaseClass.loadWorkdata();

        WorkoutAdapter adapter = new WorkoutAdapter(getContext(),R.layout.workoutlistdesign,arrayList,5);
        listView.setAdapter(adapter);




    }

    public void callAlarm() {

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), R.raw.alertmusic);
        try {
            mediaPlayer.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
        }


    }

}