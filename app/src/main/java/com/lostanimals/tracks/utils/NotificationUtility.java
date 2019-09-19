package com.lostanimals.tracks.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import com.lostanimals.tracks.R;

import static android.support.v4.content.ContextCompat.getSystemService;


public class NotificationUtility {
    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder builder;

    private static final CharSequence CHANNEL_NAME = "Tracks";
    private static final String CHANNEL_DESCRIPTION = "Tracks Channel";
    private static final String CHANNEL_ID = "channel_0";

    public static void createNotification(Context context, String title, String text, Boolean autoCancel){
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationManager = getSystemService(context, NotificationManager.class);

        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);

            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            builder.setContentTitle(title);
            builder.setContentText(text);
            builder.setAutoCancel(autoCancel);
        }
    }

    public static void createNotification(Context context, String title, String text, Boolean autoCancel, PendingIntent pendingIntent){
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationManager = getSystemService(context, NotificationManager.class);


        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);

            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            builder.setContentTitle(title);
            builder.setContentText(text);
            builder.setAutoCancel(autoCancel);
        }
    }

    public static void displayNotification(int notificationID) {
        notificationManager.notify(notificationID, builder.build());
    }
}
