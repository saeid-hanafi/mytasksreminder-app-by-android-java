package com.example.todolist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, exportSchema = true, entities = {Task.class})
public abstract class TasksDatabase extends RoomDatabase {
    private static TasksDatabase tasksDatabase;

    public static TasksDatabase getTasksDatabase(Context context) {
        if (tasksDatabase == null)
            tasksDatabase = Room.databaseBuilder(context.getApplicationContext(), TasksDatabase.class, "db_tasks")
                    .allowMainThreadQueries()
                    .build();

        return tasksDatabase;
    }

    public abstract TasksDao getTasksDao();
}
