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
        }
    }
}
