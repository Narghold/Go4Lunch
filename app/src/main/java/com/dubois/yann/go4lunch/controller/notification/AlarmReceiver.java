package com.dubois.yann.go4lunch.controller.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadcastReceiver", "AlarmManger received!");
        NotificationJobService.scheduleJob(context);
    }
}
