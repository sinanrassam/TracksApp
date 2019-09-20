package com.lostanimals.tracks.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import com.lostanimals.tracks.R;

import static android.support.v4.content.ContextCompat.getSystemService;

/**
 * @version 1.0
 * Class was create to save on code re-use when having multiple classes that call similar methods.
 */
public class NotificationUtility {
	private static final CharSequence CHANNEL_NAME = "Tracks";
	private static final String CHANNEL_DESCRIPTION = "Tracks Channel";
	private static final String CHANNEL_ID = "channel_0";
	private static NotificationManager notificationManager;
	private static NotificationCompat.Builder builder;
	private static NotificationChannel notificationChannel;
	
	/**
	 * Method for creating the notification, manager and channel.
	 *
	 * @param context       The context of the calling class / activity.
	 * @param title         The notification title.
	 * @param text          The body of the notification.
	 * @param autoCancel    Boolean value for setting the notification to close when tapped by the user.
	 * @param pendingIntent Non-required variable. Intent to be started if action is taken on the notification.
	 */
	public static void createNotification(Context context, String title, String text, Boolean autoCancel, PendingIntent pendingIntent) {
		if (notificationChannel == null) {
			setChannel();
		}
		
		if (notificationManager == null) {
			setManager(context);
		}
		
		builder = new NotificationCompat.Builder(context, CHANNEL_ID);
		builder.setSmallIcon(R.drawable.ic_launcher_foreground);
		builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
		
		if (pendingIntent != null) {
			setPendingIntent(pendingIntent);
		}
		
		builder.setContentTitle(title);
		builder.setContentText(text);
		builder.setAutoCancel(autoCancel);
	}
	
	/**
	 * Simple method to set channel if not already.
	 */
	private static void setChannel() {
		notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
				NotificationManager.IMPORTANCE_DEFAULT);
		notificationChannel.setDescription(CHANNEL_DESCRIPTION);
		
	}
	
	/**
	 * Simple method to set the manager if not already.
	 *
	 * @param context The parent context.
	 */
	private static void setManager(Context context) {
		notificationManager = getSystemService(context, NotificationManager.class);
	}
	
	/**
	 * Simple method to set the PendingIntent of a notification if required.
	 *
	 * @param pendingIntent The intent to start.
	 */
	private static void setPendingIntent(PendingIntent pendingIntent) {
		//TODO: Jason: add code here to add the PendingIntent stuff.
	}
	
	/**
	 * Create and show the notification.
	 *
	 * @param notificationID passed in by the calling class, used to distinguish who created the notification.
	 */
	public static void displayNotification(int notificationID) {
		notificationManager.notify(notificationID, builder.build());
	}
}