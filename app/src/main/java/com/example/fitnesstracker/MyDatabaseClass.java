package com.example.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MyDatabaseClass extends SQLiteOpenHelper {
    Context context;


    public static final String DATABASE_NAME = "FITNESSDATA";
    public static final int DATABASE_VERSION = 1;

    public static final String USERDATA = "USERDATA";
    public static final String ID = "_id";
    public static final String NAME = "Full_Name";
    public static final String Age = "Age";
    public static final String Height = "Height";
    public static final String WEIGHT = "Weight";
    public static final String BMI = "BMI";
    public static final String REMARK = "Remark";


    public static final String WORK = "Work_Activity";
    public static final String WORKOUT_NAME = "Workout_Name";
    public static final String RESTTIME = "Rest_Time";
    public static final String DATE = "Date";

    public static final String USERDATATABLEQUERY = " CREATE TABLE " + USERDATA + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            NAME + " VARCHAR(255) ," + Age + " INTEGER ," + Height + " DOUBLE ," + WEIGHT + " DOUBLE ,"
            + BMI + " DOUBLE ," +REMARK + " VARCHAR(255));";

    public static final String WORKACTIVITYTABLE = " CREATE TABLE " + WORK + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            WORKOUT_NAME + " VARCHAR(255) ," + RESTTIME + " INTEGER ," + DATE + " VARCHAR(255));";


    public MyDatabaseClass(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Toast.makeText(context.getApplicationContext(), "Hello!", Toast.LENGTH_SHORT).show();

        try
        {
            sqLiteDatabase.execSQL(USERDATATABLEQUERY);
            sqLiteDatabase.execSQL(WORKACTIVITYTABLE);

            Toast.makeText(context.getApplicationContext(), "Table Created!", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "Table Not Created!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertUserData(String name, int age, double height, double weight, double bmi, String Remark){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(Age,age);
        contentValues.put(Height,height);
        contentValues.put(WEIGHT,weight);
        contentValues.put(BMI,bmi);
        contentValues.put(REMARK,Remark);

        database.insert(USERDATA,null,contentValues);
        database.close();


    }

    public void insertIntoWorkout(String Wname, String Rtime, String dates){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(WORKOUT_NAME,Wname);
        contentValues.put(RESTTIME,Rtime);
        contentValues.put(DATE,dates);

        database.insert(WORK,null,contentValues);
        database.close();

    }
    public void updateUserData(String name, int age, double height, double weight, double bmi, String Remark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(Age,age);
        contentValues.put(Height,height);
        contentValues.put(WEIGHT,weight);
        contentValues.put(BMI,bmi);
        contentValues.put(REMARK,Remark);
        String[] whereArgs = {"1"};
        db.update(USERDATA,contentValues,"_id = ?",whereArgs);
        db.close();

    }


    public ArrayList<UserDetailsModel> loadUserdata(){
        ArrayList<UserDetailsModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String readall = "SELECT * FROM " + USERDATA;
        Cursor cursor = sqLiteDatabase.rawQuery(readall,null);
        if (cursor.moveToFirst()){
            do {
                list.add(new UserDetailsModel(cursor.getInt(0), cursor.getInt(2),cursor.getDouble(3)
                        , cursor.getDouble(4), cursor.getInt(5), cursor.getString(1), cursor.getString(6)));


            }
            while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;

    }
    public ArrayList<WorkoutModelClass> loadWorkdata(){
        ArrayList<WorkoutModelClass> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String readall = "SELECT * FROM " + WORK;
        Cursor cursor = sqLiteDatabase.rawQuery(readall,null);
        if (cursor.moveToLast()){
            do {
                list.add(new WorkoutModelClass(cursor.getString(1), cursor.getString(2),cursor.getString(3)));            }
            while (cursor.moveToPrevious());
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;

    }
}
