package com.example.workoutapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity{

    public SQLiteDatabase db = null;
    public DatabaseOpenHelper dbHelper = null;
    Cursor mCursor;

    ArrayAdapter aAdapter;
    ArrayList<String[]> list;
    ListView mlist;

    final static String[] all_columns = { DatabaseOpenHelper.ID, DatabaseOpenHelper.ACT, DatabaseOpenHelper.REP, DatabaseOpenHelper.SET, DatabaseOpenHelper.WEIGHT, DatabaseOpenHelper.NOTE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);

        mlist = (ListView)findViewById(R.id.exerciseListV);

        aAdapter = new ArrayAdapter(this, R.layout.line_view);
        mlist.setAdapter(aAdapter);



        dbHelper = new DatabaseOpenHelper(this);

        db = dbHelper.getWritableDatabase();


        mCursor = db.query(dbHelper.DBNAME, all_columns, null, null, null, null, null);


        list = new ArrayList<String[]>();

        mCursor.moveToFirst();

        do {
            int nameC = mCursor.getColumnIndex(dbHelper.ACT);
            int repC = mCursor.getColumnIndex(dbHelper.REP);
            int setC = mCursor.getColumnIndex(dbHelper.SET);
            int weightC = mCursor.getColumnIndex(dbHelper.WEIGHT);
            int noteC = mCursor.getColumnIndex(dbHelper.NOTE);

            String nameStr = mCursor.getString(nameC);
            String repStr = mCursor.getString(repC);
            String setStr = mCursor.getString(setC);
            String weightStr = mCursor.getString(weightC);
            String noteStr = mCursor.getString(noteC);

            String[] temp = {nameStr, repStr, setStr, weightStr, noteStr};
            aAdapter.add(nameStr);
            list.add(temp);
        } while (mCursor.moveToNext());



        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nameStr = list.get(position)[0];
                String repStr = list.get(position)[1];
                String setStr = list.get(position)[2];
                String weightStr = list.get(position)[3];
                String noteStr = list.get(position)[4];

                Snackbar.make(findViewById(R.id.viewMsg), "Activity: " + nameStr + "\t Reps: " + repStr + " Sets: " + setStr + " Weight: " + weightStr + "\nNote: " + noteStr, Snackbar.LENGTH_INDEFINITE).show();
            }
        });



        mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Completed: " + aAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                aAdapter.remove(aAdapter.getItem(position));
                list.remove(list.get(position));

                return true;
            }
        });



    }
}