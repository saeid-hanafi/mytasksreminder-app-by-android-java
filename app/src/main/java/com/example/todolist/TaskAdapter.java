package com.example.todolist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                notifyItemChanged(i);
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSearchResult(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TackViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private ImageView deleteIcon;
        private TextView lastUpdate;

        public TackViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_task);
            deleteIcon = (ImageView) itemView.findViewById(R.id.iv_delete_icon);
            lastUpdate = itemView.findViewById(R.id.tv_last_timestamp);
        }

        public void bindContent(Task task) {
//            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(task.isCompleted());
            checkBox.setText(task.getTitle());
            lastUpdate.setText(task.getLast_update());
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.deleteTaskByID(task);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    eventListener.editTask(task);
                    return false;
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isPressed()) {
                        task.setCompleted(isChecked);
                        eventListener.changeCheckedStatus(task);
                    }
                }
            });
        }
    }

    public interface taskAdapterEventListener {
        void deleteTaskByID(Task task);
        void editTask(Task task);
        void changeCheckedStatus(Task task);
    }
}
