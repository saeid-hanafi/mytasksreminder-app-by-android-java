package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TackViewHolder> {

    @NonNull
    @Override
    public TackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TackViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class TackViewHolder extends RecyclerView.ViewHolder {

        public TackViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
