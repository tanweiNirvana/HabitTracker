package com.example.a46146.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a46146.habittracker.data.HabitDbHelper;

import static com.example.a46146.habittracker.data.HabitContract.HabitEntry.*;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView textView;
    private String data = "";
    private HabitDbHelper dbHelper;
    private static int NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        dbHelper = new HabitDbHelper(this);
        dbHelper.getWritableDatabase();

        btn = (Button) findViewById(R.id.btn);
        btn.setText(R.string.create_data);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (NUM) {
                    case 0:
                        createDB();
                        btn.setText(R.string.update_data);
                        NUM++;
                        break;
                    case 1:
                        updateDB();
                        btn.setText(R.string.delete_data);
                        NUM++;
                        break;
                    case 2:
                        deleteDB();
                        btn.setText(R.string.display_data);
                        NUM++;
                        break;
                    case 3:
                        retrieveDB();
                        textView.setText(data);
                        break;
                }
            }
        });
    }

    //create database
    private void createDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_HABIT_NAME, getString(R.string.Walk_the_dog));
        values.put(COLUMN_HABIT_TIME, 30);
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_HABIT_NAME, getString(R.string.saxophone));
        values.put(COLUMN_HABIT_TIME, 20);
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_HABIT_NAME, getString(R.string.Program));
        values.put(COLUMN_HABIT_TIME, 60);
        db.insert(TABLE_NAME, null, values);
        values.clear();
    }

    //update database
    private void updateDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_HABIT_TIME, 120);
        db.update(TABLE_NAME, values, COLUMN_HABIT_NAME + " = ?", new String[]{getString(R.string.Program)});
    }

    //read All Habits
    public Cursor readAllHabits() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //retrieve database
    private void retrieveDB() {
        Cursor cursor = readAllHabits();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_HABIT_NAME));
                int time = cursor.getInt(cursor.getColumnIndex(COLUMN_HABIT_TIME));
                data += name + ":  " + time + "\n";
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    //delete database
    private void deleteDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_HABIT_TIME + "<?", new String[]{"30"});
    }
}
