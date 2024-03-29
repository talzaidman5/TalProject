package com.example.project.activitis;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.project.R;
import com.example.project.activitis.client.ActivityMainForm;

public class newEventDialog extends AppCompatDialogFragment {
    private View view;
    private Button dialog_BTN_confirm;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_activity, null);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog_BTN_confirm = view.findViewById(R.id.dialog_BTN_confirm);
        dialog_BTN_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(view.getContext(), ActivityMainForm.class);
                startActivity(intent);
            }
        });

        return dialog;
    }




}

