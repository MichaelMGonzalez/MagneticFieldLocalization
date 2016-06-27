package com.ashmp.magneticfieldlocalizer.activity.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ashmp.magneticfieldlocalizer.R;
import com.ashmp.magneticfieldlocalizer.utils.FileIO;

import java.io.File;

/**
 * Created by Michael on 6/27/2016.
 */
public class SetIPDialog extends DialogFragment {

    private EditText ipLabelOne, ipLabelTwo, ipLabelThree, ipLabelFour;
    public static final String IPFILE = "connectionIP";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_ip, null);
        // Get the labels
        ipLabelOne   = (EditText) v.findViewById(R.id.ip_one);
        ipLabelTwo   = (EditText) v.findViewById(R.id.ip_two);
        ipLabelThree = (EditText) v.findViewById(R.id.ip_three);
        ipLabelFour  = (EditText) v.findViewById(R.id.ip_four);
        // Load the saved IP

        loadIP(v);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Set IP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String ip = ipLabelOne.getText()   + "." +
                                    ipLabelTwo.getText()   + "." +
                                    ipLabelThree.getText() + "." +
                                    ipLabelFour.getText();
                        FileIO.saveString(IPFILE, ip, v.getContext());
                        System.out.println(ip);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SetIPDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void loadIP(View v) {
        String ipString = FileIO.retrieveStringFromFile(IPFILE, v.getContext());
        String[] ipArray = ipString.split("\\.");
        System.out.println(ipString);
        System.out.println(ipArray.length);
        ipLabelOne.setText(ipArray[0]);
        ipLabelTwo.setText(ipArray[1]);
        ipLabelThree.setText(ipArray[2]);
        ipLabelFour.setText(ipArray[3]);
    }

    public interface SetIPDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    SetIPDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SetIPDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
