package com.example.workoutapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Add2DB extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private DatabaseOpenHelper dbHelper = null;

    final static String[] all_columns = { DatabaseOpenHelper.ID, DatabaseOpenHelper.ACT, DatabaseOpenHelper.REP, DatabaseOpenHelper.SET, DatabaseOpenHelper.WEIGHT, DatabaseOpenHelper.NOTE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        dbHelper = new DatabaseOpenHelper(this);


    }

    @Override
    public void onPause() {
        super.onPause();
        if(db != null) db.close();
    }

    public void onAdd(View v) {
        EditText nameText = (EditText)findViewById(R.id.inputName);
        EditText repText = (EditText)findViewById(R.id.inputRep);
        EditText setText = (EditText)findViewById(R.id.inputSet);
        EditText weightText = (EditText)findViewById(R.id.inputWeight);
        EditText noteText = (EditText)findViewById(R.id.inputNote);
        String name = nameText.getText().toString();
        String rep = repText.getText().toString();
        String set = setText.getText().toString();
        String weight = weightText.getText().toString();
        String note = noteText.getText().toString();

        if(name.equals("") || rep.equals("") || set.equals("") || weight.equals("")) {
            Toast.makeText(this, "Please fill in the form", Toast.LENGTH_SHORT).show();
        } else {
            boolean flag = false;
            db = dbHelper.getWritableDatabase();

            Cursor mCursor = db.query(dbHelper.DBNAME, all_columns, null, null, null, null, null);
            mCursor.moveToFirst();
            int nameC = mCursor.getColumnIndex(dbHelper.ACT);
            if(mCursor.getCount() > 0) {
                do {
                    String nameStr = mCursor.getString(nameC);
                    if(name.equals(nameStr)) {
                        flag = true;
                        break;
                    }
                } while (mCursor.moveToNext());
            }

            if(flag == false) {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseOpenHelper.ACT, name);
                cv.put(DatabaseOpenHelper.REP, rep);
                cv.put(DatabaseOpenHelper.SET, set);
                cv.put(DatabaseOpenHelper.WEIGHT, weight);
                cv.put(DatabaseOpenHelper.NOTE, note);
                db.insert(DatabaseOpenHelper.DBNAME,null,cv);
                finish();
            } else {
                Toast.makeText(this, name + " already exists!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onCancel(View v) {
        finish();
    }
}