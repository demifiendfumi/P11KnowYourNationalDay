package com.myapplicationdev.android.demodialog.p11_knowyournationalday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Message from: 123");
        dialogBuilder.setMessage("new test");
        dialogBuilder.setCancelable(true);
        //dialogBuilder.setPositiveButton();

        AlertDialog alertdialog = dialogBuilder.create();
        //alertdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertdialog.show();
    }
}
