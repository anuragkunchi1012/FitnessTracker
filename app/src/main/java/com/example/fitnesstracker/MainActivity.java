package com.example.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static final String[] tabs = {"Workout","BMI Calculator", "History"};
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    Chronometer chronometer;
    TextView onExit, onPause, onReset;
    ImageView exitImage;
    private boolean running;
    private long pauseOffset=0;
    MyDatabaseClass myDatabaseClass = new MyDatabaseClass(this);
    ArrayList <UserDetailsModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager2 = (ViewPager2) findViewById(R.id.viewpager2);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);




        toolbar.setTitle("Fitness Tracker");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menulist);

        PageViewerClass pageViewerClass = new PageViewerClass(this);
        viewPager2.setAdapter(pageViewerClass);

        new TabLayoutMediator(tabLayout, viewPager2,(tab, position) -> tab.setText(tabs[position])).attach();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menulist, menu);
        return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.timmer: {
                //Toast.makeText(getApplicationContext(), "Second Time called", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setCancelable(false);
                View dialogView = getLayoutInflater().inflate(R.layout.timerviewer, null);
                chronometer = (Chronometer) dialogView.findViewById(R.id.mychronometer);
                chronometer.setFormat("Timer : %s ");
                onStartChronometer();
                onExit = (TextView) dialogView.findViewById(R.id.exit);
                onPause = (TextView) dialogView.findViewById(R.id.pause);
                onReset = (TextView) dialogView.findViewById(R.id.reset);
                exitImage = (ImageView) dialogView.findViewById(R.id.exitimage);

                builder.setView(dialogView);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                onPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onPause.getText().toString().equals("Pause")) {
                            onPauseChronometer();
                            onPause.setText("Resume");
                        } else {
                            onStartChronometer();
                            onPause.setText("Pause");
                        }
                    }
                });

                onReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRestChronometer();
                    }
                });


                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.color.purple_500));
                onExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                        running=false;

                    }
                });
                exitImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                        running=false;

                    }
                });


                //Toast.makeText(getApplicationContext(), "Timmer Click", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.user:{
                startActivity(new Intent(MainActivity.this, UserData.class));


                break;
            }

        }
        return true;
    }

    public void onStartChronometer(){
        if (!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            Toast.makeText(getApplicationContext(), "Timmer start", Toast.LENGTH_SHORT).show();
            running=true;
        }

    }
    public void onPauseChronometer(){
        if (running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running=false;
        }

    }

    public void onRestChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;


    }


}