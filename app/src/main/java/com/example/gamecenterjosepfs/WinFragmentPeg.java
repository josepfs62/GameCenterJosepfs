package com.example.gamecenterjosepfs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.gamecenterjosepfs.db.DbScores;

public class WinFragmentPeg extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        int pegs = args.getInt("pegs",0);
        int time = args.getInt("time", 0);
        String userName = args.getString("userName");
        builder.setMessage(R.string.win)
                .setPositiveButton("ok", (new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which) {
                        DbScores dbScores = new DbScores(getActivity());
                        long id = dbScores.insertScore(userName, String.valueOf(time), String.valueOf(pegs), "PEGS");

                        if (id > 0){
                            Toast.makeText(getActivity(), "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
                        }
                    }
                }));
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
