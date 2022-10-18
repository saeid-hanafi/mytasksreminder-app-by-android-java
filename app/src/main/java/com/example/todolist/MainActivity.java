package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogItem.addNewDialogCallBack, TaskAdapter.taskAdapterEventListener {
    private TaskAdapter taskAdapter;
    private SQLiteHelper sqLiteHelper;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskAdapter = new TaskAdapter(MainActivity.this);
        sqLiteHelper = new SQLiteHelper(this);

        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);

        List<Task> tasks = sqLiteHelper.getAllTasks();
        if (tasks != null) {
            taskAdapter.addAllTasks(tasks);
        }

        View fabAddTask = findViewById(R.id.fab_add_task);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogItem dialogItem = new DialogItem(false);
                dialogItem.show(getSupportFragmentManager(), null);
            }
        });

        View deleteAllTasksBtn = findViewById(R.id.remove_all_tasks_btn);
        deleteAllTasksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.deleteAllTasks();
                taskAdapter.deleteAllTasks();
            }
        });
    }

    @Override
    public void addNewTaskSuccess(Task task) {
        Task result = sqLiteHelper.addNewTask(task);
        if (result.getId() > 0) {
            taskAdapter.addNewTask(result);
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void editTaskSuccess(Task task) {
        int result = sqLiteHelper.updateTask(task);
        if (result > 0) {
            taskAdapter.updateTask(task);
        }
    }

    @Override
    public void deleteTaskByID(Task task) {
        int result = sqLiteHelper.deleteTask(task);
        if (result > 0) {
            taskAdapter.deleteTask(task);
        }
    }

    @Override
    public void editTask(Task task) {
        DialogItem dialogItem = new DialogItem(true);
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        dialogItem.setArguments(bundle);
        dialogItem.show(getSupportFragmentManager(), null);
    }
}