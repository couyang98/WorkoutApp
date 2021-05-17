package com.example.workoutapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class EditActivity extends AppCompatActivity {

    public SQLiteDatabase db = null;
    public DatabaseOpenHelper dbHelper = null;
    SimpleCursorAdapter myAdapter;
    ListView mlist;
    Cursor mCursor;

    final static String[] all_columns = { DatabaseOpenHelper.ID, DatabaseOpenHelper.ACT, DatabaseOpenHelper.REP, DatabaseOpenHelper.SET, DatabaseOpenHelper.WEIGHT, DatabaseOpenHelper.NOTE};
    final static String[] print_columns = { DatabaseOpenHelper.ACT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_workout);
        mlist = (ListView)findViewById(R.id.exerciseList);
        dbHelper = new DatabaseOpenHelper(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        db = dbHelper.getWritableDatabase();

        mCursor = db.query(dbHelper.DBNAME, all_columns, null, null, null, null, null);
        myAdapter = new SimpleCursorAdapter(this, R.layout.line, mCursor, print_columns, new int[] {R.id.display}, 0);
        mlist.setAdapter(myAdapter);

        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
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

                Intent i = new Intent(view.getContext(), EditDB.class);
                i.putExtra("POSITION", position);
                startActivity(i);

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (db != null) db.close();
        mCursor.close();
    }

    public void onEdit(View v) {
        Intent i = new Intent(this, Add2DB.class);
        startActivity(i);
    }


}