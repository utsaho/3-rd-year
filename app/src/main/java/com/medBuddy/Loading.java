package com.medBuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loading {
    Activity activity;
    AlertDialog dialog;
    Loading(Activity act){
        activity = act;

    }
    void startLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_loading, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    void stopLoading(){
        dialog.dismiss();
    }
}
