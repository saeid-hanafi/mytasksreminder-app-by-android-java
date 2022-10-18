package com.example.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class DialogItem extends DialogFragment {
    private addNewDialogCallBack callBack;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callBack= (addNewDialogCallBack) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item, null, false);
        builder.setView(view);

        final TextInputEditText taskTitle = view.findViewById(R.id.add_new_task_text);
        View addNewBtn = view.findViewById(R.id.add_new_task_btn);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.setTitle(Objects.requireNonNull(taskTitle.getText()).toString());
                task.setCompleted(false);
                callBack.addNewTaskSuccess(task);
                dismiss();
            }
        });
        return builder.create();
    }

    public interface addNewDialogCallBack {
        void addNewTaskSuccess(Task task);
    }
}
