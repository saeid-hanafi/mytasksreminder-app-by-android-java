package com.example.todolist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TackViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    private taskAdapterEventListener eventListener;

    public TaskAdapter(taskAdapterEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public TackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TackViewHolder holder, int position) {
        holder.bindContent(tasks.get(position));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAllTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void addNewTask(Task task) {
        tasks.add(0, task);
        notifyItemInserted(0);
    }

    public void deleteTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteAllTasks() {
        tasks.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TackViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private ImageView deleteIcon;

        public TackViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_task);
            deleteIcon = itemView.findViewById(R.id.iv_delete_icon);
        }

        public void bindContent(Task task) {
            checkBox.setText(task.getTitle());
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.deleteTaskByID(task);
                }
            });
        }
    }

    public interface taskAdapterEventListener {
        void deleteTaskByID(Task task);
    }
}
