package com.example.workoutapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class EditDB extends AppCompatActivity {

    int position;

    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;

    final static String[] all_columns = { DatabaseOpenHelper.ID, DatabaseOpenHelper.ACT, DatabaseOpenHelper.REP, DatabaseOpenHelper.SET, DatabaseOpenHelper.WEIGHT, DatabaseOpenHelper.NOTE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        dbHelper = new DatabaseOpenHelper(this);

        Intent i = getIntent();
        position = i.getIntExtra("POSITION", 0);

        TextView name = (TextView)findViewById(R.id.promptNameE);
        EditText repText = (EditText)findViewById(R.id.inputRepE);
        EditText setText = (EditText)findViewById(R.id.inputSetE);
        EditText weightText = (EditText)findViewById(R.id.inputWeightE);
        EditText noteText = (EditText)findViewById(R.id.inputNoteE);

        db = dbHelper.getWritableDatabase();

        Cursor mCursor = db.query(dbHelper.DBNAME, all_columns, null, null, null, null, null);
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

        repText.setHint(repStr);
        setText.setHint(setStr);
        weightText.setHint(weightStr);
        noteText.setText(noteStr);

        name.setText("Name: " + nameStr);

    }

    public void onEdit(View v) {

        TextView name = (TextView)findViewById(R.id.promptNameE);
        EditText repText = (EditText)findViewById(R.id.inputRepE);
        EditText setText = (EditText)findViewById(R.id.inputSetE);
        EditText weightText = (EditText)findViewById(R.id.inputWeightE);
        EditText noteText = (EditText)findViewById(R.id.inputNoteE);

        Cursor mCursor = db.query(dbHelper.DBNAME, all_columns, null, null, null, null, null);
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

        ContentValues cn = new ContentValues();
        cn.put(DatabaseOpenHelper.ACT, nameStr);
        if(repText.getText().toString().equals("")) {
            cn.put(DatabaseOpenHelper.REP, repStr);
        } else {
            cn.put(DatabaseOpenHelper.REP, repText.getText().toString());
        }

        if(setText.getText().toString().equals("")) {
            cn.put(DatabaseOpenHelper.SET, setStr);

        } else {
            cn.put(DatabaseOpenHelper.SET, setText.getText().toString());
        }

        if(weightText.getText().toString().equals("")) {
            cn.put(DatabaseOpenHelper.WEIGHT, weightStr);
        } else {
            cn.put(DatabaseOpenHelper.WEIGHT, weightText.getText().toString());
        }

        cn.put(DatabaseOpenHelper.NOTE, noteText.getText().toString());

        db.update(DatabaseOpenHelper.DBNAME, cn, DatabaseOpenHelper.ACT + "=?", new String[] { nameStr });

        finish();

    }

    public void onCancel(View v) {
        finish();
    }

}

