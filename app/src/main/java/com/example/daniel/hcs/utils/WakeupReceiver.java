package com.example.daniel.hcs.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.daniel.hcs.MainActivity;
import com.example.daniel.hcs.NotificationActivity;
import com.example.daniel.hcs.R;

public class WakeupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ALAAAARM", "Popij tableticu da ti bude bolje!");
        NotificationSchelduer.showNotification(context, NotificationActivity.class,
                "Take pill!", "Please!");
    }
}
