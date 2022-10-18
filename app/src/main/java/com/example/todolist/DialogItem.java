package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class DialogItem extends DialogFragment {
    private addNewDialogCallBack callBack;
    private boolean isEdit;
    private Task task;

    public DialogItem(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callBack= (addNewDialogCallBack) context;
        if (isEdit && getArguments() != null) {
            task = getArguments().getParcelable("task");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item, null, false);
        builder.setView(view);

        TextInputLayout taskInput = view.findViewById(R.id.task_input_layout);
        TextInputEditText taskTitle = view.findViewById(R.id.add_new_task_text);
        MaterialButton addNewBtn = view.findViewById(R.id.add_new_task_btn);

        if (isEdit) {
            String oldTitle = task.getTitle();
            if (oldTitle != null) {
                taskTitle.setText(oldTitle);
                TextView dialogMainTitle = view.findViewById(R.id.task_dialog_main_title);
                dialogMainTitle.setText(R.string.edit_task);
                addNewBtn.setText(R.string.edit_btn);

                addNewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (taskTitle.length() > 0) {
                            String newTitle = Objects.requireNonNull(taskTitle.getText()).toString();
                            if (newTitle.equalsIgnoreCase(oldTitle)) {
                                dismiss();
                            }else{
                                task.setTitle(newTitle);
                                task.setLast_update(DialogItem.this.getCurrentTimestamp());
                                callBack.editTaskSuccess(task);
                                dismiss();
                            }
                        }else{
                            taskInput.setError("Title is empty!");
                        }
                    }
                });
            }else{
                dismiss();
            }
        }else{
            addNewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (taskTitle.length() > 0) {
                        Task task = new Task();
                        task.setTitle(Objects.requireNonNull(taskTitle.getText()).toString());
                        task.setCompleted(false);
                        task.setLast_update(DialogItem.this.getCurrentTimestamp());
                        callBack.addNewTaskSuccess(task);
                        dismiss();
                    }else{
                        taskInput.setError("Title is empty!");
                    }
                }
            });
        }
        return builder.create();
    }

    @SuppressLint("SimpleDateFormat")
    public String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public interface addNewDialogCallBack {
        void addNewTaskSuccess(Task task);
        void editTaskSuccess(Task task);
    }
}
