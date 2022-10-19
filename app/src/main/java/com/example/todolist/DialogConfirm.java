package com.example.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

public class DialogConfirm extends DialogFragment {
    private boolean isSingle;
    private dialogConfirmEventListener eventListener;

    public DialogConfirm(dialogConfirmEventListener eventListener, boolean isSingle) {
        this.isSingle = isSingle;
        this.eventListener = eventListener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null, false);
        builder.setView(view);

        View okBtn = view.findViewById(R.id.btn_dialog_ok);
        View cancelBtn = view.findViewById(R.id.btn_dialog_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (isSingle) {
            TextView textView = view.findViewById(R.id.tv_dialog_confirm);
            textView.setText(R.string.ask_single_delete);

            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getArguments() != null) {
                        Task task = getArguments().getParcelable("delTask");
                        eventListener.deleteSingleListener(task);
                        dismiss();
                    }else {
                        dismiss();
                    }
                }
            });
        }else{
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.deleteAllListener();
                    dismiss();
                }
            });
        }

        return builder.create();
    }

    public interface dialogConfirmEventListener {
        void deleteAllListener();
        void deleteSingleListener(Task task);
    }
}
