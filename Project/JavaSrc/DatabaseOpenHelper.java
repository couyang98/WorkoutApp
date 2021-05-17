package com.example.workoutapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    final static String DBNAME = "exerciseTable";
    final static String ID = "_id";
    final static String ACT = "name";
    final static String REP = "rep";
    final static String SET = "set1";
    final static String WEIGHT = "weight";
    final static String NOTE = "note";
    final private static String CREATE_CMD = "CREATE TABLE " + DBNAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACT + " TEXT NOT NULL, " + REP + " TEXT NOT NULL, " + SET + " TEXT NOT NULL, " + WEIGHT + " TEXT NOT NULL, " + NOTE + " TEXT NOT NULL)";


    final private static Integer VERSION = 1;
    final private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, "todo_db", null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("\n\n\n\nHERE!!!!!!!!!!!!\n\n");
        sqLiteDatabase.execSQL(CREATE_CMD);
        ContentValues values = new ContentValues();

        values.put(ACT, "Sit ups");
        values.put(REP, "10");
        values.put(SET, "5");
        values.put(WEIGHT, "100");
        values.put(NOTE, "CALORIES!!!!!!!!!!!");
        sqLiteDatabase.insert(DBNAME, null, values);
        values.clear();

        values.put(ACT, "Push ups");
        values.put(REP, "10");
        values.put(SET, "5");
        values.put(WEIGHT, "100");
        values.put(NOTE, "SPARTA!!!!!!!!");
        sqLiteDatabase.insert(DBNAME, null, values);
        values.clear();

        values.put(ACT, "Crunch");
        values.put(REP, "10");
        values.put(SET, "5");
        values.put(WEIGHT, "100");
        values.put(NOTE, "AHHH");
        sqLiteDatabase.insert(DBNAME, null, values);
        values.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO Auto-generated method stub
    }

}
