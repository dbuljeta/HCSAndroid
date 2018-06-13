package com.example.daniel.hcs.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.daniel.hcs.R;

public class NotificationSchelduer {

    private static final String BUNDLE_PILL_ID = "pill_id";
    private static final String BUNDLE_INTAKE_ID = "intake_id";

    public static void showNotification(Context context, Class<?> cls, String title, String content, Long pillId, Long intakeId)
    {
        Bundle bundle = new Bundle();
        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        bundle.putLong(BUNDLE_PILL_ID, pillId);
        bundle.putLong(BUNDLE_INTAKE_ID, intakeId);
        notificationIntent.putExtras(bundle);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                100,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        Notification notification = builder.setContentTitle(title)
                .setContentText(content).setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);
    }
}
