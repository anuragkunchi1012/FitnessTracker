package com.example.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserData extends AppCompatActivity {
    MyDatabaseClass myDatabaseClass = new MyDatabaseClass(this);
    ArrayList<UserDetailsModel> arrayList = new ArrayList<>();
    LinearLayout addData, viewData;
    Toolbar toolbar;

    EditText name, age, weight, height;
    TextView Tname, Tage, Tweight, Theight, Tremark, TBMI;

    Button saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        addData = (LinearLayout) findViewById(R.id.addData);
        viewData = (LinearLayout) findViewById(R.id.viewData);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        saveData = (Button) findViewById(R.id.saveDetails);
        toolbar = findViewById(R.id.toolbar);
        Tname = findViewById(R.id.showname);
        Tage = findViewById(R.id.showage);
        Theight = findViewById(R.id.showheight);
        Tweight = findViewById(R.id.showweight);
        TBMI = findViewById(R.id.showbmi);
        Tremark = findViewById(R.id.showremark);



        toolbar.setTitle("User Data");

        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.usereditoption);

        arrayList = myDatabaseClass.loadUserdata();
        System.out.println("Printing: "+arrayList.size());
        if (arrayList.size()<=0){
            Toast.makeText(getApplicationContext(), "user data is empty", Toast.LENGTH_SHORT).show();
            viewData.setVisibility(View.GONE);
            addData.setVisibility(View.VISIBLE);


        }
        else {
            viewData.setVisibility(View.VISIBLE);
            addData.setVisibility(View.GONE);
            Tname.setText(arrayList.get(0).getName());
            Tage.setText(""+arrayList.get(0).getAge());
            Theight.setText(String.format("%.2f",arrayList.get(0).getHeight()));
            Tweight.setText(String.format("%.2f",arrayList.get(0).getWeight()));
            TBMI.setText(String.format("%.2f",arrayList.get(0).getBmi()));
            Tremark.setText(arrayList.get(0).Remark);

        }

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
                startActivity(new Intent(UserData.this,MainActivity.class));

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usereditoption, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.editdata){
            viewData.setVisibility(View.GONE);
            addData.setVisibility(View.VISIBLE);

            name.setText(Tname.getText().toString());
            age.setText(Tage.getText().toString());
            height.setText(Theight.getText().toString());
            weight.setText(Tweight.getText().toString());
            saveData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nm = name.getText().toString();
                    String ag = age.getText().toString();
                    String ht = height.getText().toString();
                    String wt = weight.getText().toString();
                    String Remark=null;

                    if (nm.trim().length()>0 && ag.trim().length()>0 && ht.trim().length()>0 && wt.trim().length()>0 ){
                        double hts = Double.parseDouble(ht);
                        double wts = Double.parseDouble(wt);
                        double BMI = (double) (wts / Math.pow(hts/100,2));
                        if (BMI<16.0) Remark = "Severely Underweight";
                        if (BMI>16.0 && BMI<18.4) Remark = "Underweight";
                        if (BMI>18.5 && BMI<24.9) Remark = "Normal";
                        if (BMI>25.0 && BMI<29.9) Remark = "Overweight";
                        if (BMI>30.0 && BMI<34.9) Remark = "Moderately Obese";
                        if (BMI>35.0 && BMI<39.9) Remark = "Severely Obese";
                        if (BMI>40.0) Remark = "Morbidly Obese";
                        myDatabaseClass.updateUserData(nm,Integer.parseInt(ag),hts, wts, BMI, Remark);
                        startActivity(new Intent(UserData.this,MainActivity.class));

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();

                    }

                }
            });



        }
    return true;
    }
    public void saveUserData(){
        String nm = name.getText().toString();
        String ag = age.getText().toString();
        String ht = height.getText().toString();
        String wt = weight.getText().toString();
        String Remark=null;

        if (nm.trim().length()>0 && ag.trim().length()>0 && ht.trim().length()>0 && wt.trim().length()>0 ){
            double hts = Double.parseDouble(ht);
            double wts = Double.parseDouble(wt);
            double BMI = (double) (wts / Math.pow(hts/100,2));
            if (BMI<16.0) Remark = "Severely Underweight";
            if (BMI>16.0 && BMI<18.4) Remark = "Underweight";
            if (BMI>18.5 && BMI<24.9) Remark = "Normal";
            if (BMI>25.0 && BMI<29.9) Remark = "Overweight";
            if (BMI>30.0 && BMI<34.9) Remark = "Moderately Obese";
            if (BMI>35.0 && BMI<39.9) Remark = "Severely Obese";
            if (BMI>40.0) Remark = "Morbidly Obese";
            myDatabaseClass.insertUserData(nm,Integer.parseInt(ag),hts, wts, BMI, Remark);


        }
        else {
            Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();

        }
    }

}