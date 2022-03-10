package com.example.gamecenterjosepfs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;


public class LoseFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.lose)
                .setPositiveButton("Guardar Score", null );
        //(dialogInterface, i) -> new intent(score)
        return builder.create();
    }

}
