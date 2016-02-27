package com.example.hal.databasetest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by HAL on 2016/02/25.
 */
public class TestDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder;

        Bundle bundle = getArguments();

        int intId = bundle.getInt(DataBase.ID);
        String strName = bundle.getString(DataBase.NAME);
        int intAge = bundle.getInt(DataBase.AGE);

        String message = strName + "：" + intAge +"\nデータを削除します。 よろしいですか？";

        builder = new AlertDialog.Builder(getActivity())
                .setTitle("Warning")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }
}
