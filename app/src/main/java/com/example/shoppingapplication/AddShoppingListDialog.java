package com.example.shoppingapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddShoppingListDialog extends DialogFragment {
    private EditText editTextName;
    private Button buttonCancel;
    private Button buttonAdd;

    public interface AddShoppingListDialogListener {
        void onDialogPositiveClick(String name);
    }

    AddShoppingListDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddShoppingListDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement AddShoppingListDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_add_shopping_list, null);
//
//        editTextName = view.findViewById(R.id.editText_shopping_list_name);
//        buttonCancel = view.findViewById(R.id.button_cancel);
//        buttonAdd = view.findViewById(R.id.button_add);

//        builder.setView(view);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                if (!name.isEmpty()) {
                    listener.onDialogPositiveClick(name);
                    dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }
}
