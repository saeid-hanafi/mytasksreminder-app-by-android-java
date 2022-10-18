package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TASK_TABLE = "tbl_tasks";
    private static final String TAG = "SQLiteHelper";

    public SQLiteHelper(@Nullable Context context) {
        super(context, "db_tasks", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE "+TASK_TABLE+"(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, completed BOOLEAN DEFAULT 0)");
        }catch (SQLiteException e) {
            Log.e(TAG, "onCreate: Table did not create!!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addNewTask(Task task) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", task.getTitle());
        contentValues.put("completed", task.isCompleted());
        long result = sqLiteDatabase.insert(TASK_TABLE, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    @SuppressLint("Recycle")
    public List<Task> getAllTasks() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM "+ TASK_TABLE, null);
        List<Task> tasks = new ArrayList<>();
        if (result.moveToFirst()) {
            Task task = new Task();
            task.setId(result.getInt(0));
            task.setTitle(result.getString(1));
            task.setCompleted(result.getInt(2) == 1);
            tasks.add(task);
        }
        sqLiteDatabase.close();
        return tasks;
    }
}