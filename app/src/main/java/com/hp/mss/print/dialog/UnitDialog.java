package com.hp.mss.print.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hp.mss.print.R;
import com.hp.mss.print.model.Unit;

public class UnitDialog {
    private OnListenerUnitEvent mListener;
    private AlertDialog mDialog;

    public UnitDialog(Context context, OnListenerUnitEvent listener) {
        mListener = listener;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_unit, null);
        final EditText textEt = (EditText) view.findViewById(R.id.edtName);

        Builder builder = new Builder(context);

        builder.setTitle(context.getResources().getString(R.string.text_unit_add));
        builder.setView(view)
                .setPositiveButton(context.getResources().getString(R.string.add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = textEt.getText().toString();

                        if (mListener != null && !text.equals("")) {
                            mListener.onSaveUnit(new Unit(text));
                        }
                    }
                });

        mDialog = builder.create();
    }

    public void show() {
        mDialog.show();
    }

    public interface OnListenerUnitEvent {
        public abstract void onSaveUnit(Unit u);
    }
}