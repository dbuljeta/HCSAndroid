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

    Long pillId, intakeId;
    String pillName, pillDescription;
    public static final String BUNDLE_PILL_ID = "pill_id";
    public static final String BUNDLE_INTAKE_ID = "intake_id";
    public static final String BUNDLE_PILL_NAME = "pill_name";
    public static final String BUNDLE_PILL_DESCRIPTION = "pill_description";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ALAAAARM", "Popij tableticu da ti bude bolje!");
        pillId = intent.getLongExtra(BUNDLE_PILL_ID, 1);
        intakeId = intent.getLongExtra(BUNDLE_INTAKE_ID, 1);
        pillName = intent.getStringExtra(BUNDLE_PILL_NAME);
        pillDescription = intent.getStringExtra(BUNDLE_PILL_DESCRIPTION);
        Log.e("pillID", "pillID " + pillId);
        Log.e("pillID", "intakeID " + intakeId);
        NotificationSchelduer.showNotification(context, NotificationActivity.class,
                "Take pill!", "Please!", pillId, intakeId, pillName, pillDescription);
    }
}
