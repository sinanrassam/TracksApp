package com.lostanimals.tracks.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.app.NotificationCompat;
import com.lostanimals.tracks.R;

import java.util.Random;

import static androidx.core.content.ContextCompat.getSystemService;

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
	private static int notificationID;
	
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
		int notificationID = new Random().nextInt();
		
		if (notificationChannel == null) {
			setChannel();
		}
		
		if (notificationManager == null) {
			setManager(context);
		}
		
		Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.web_hi_res_512);
		
		builder = new NotificationCompat.Builder(context, CHANNEL_ID);
		builder.setContentIntent(pendingIntent);
		builder.setLargeIcon(largeIcon);
		builder.setSmallIcon(R.drawable.ic_notifications);
		builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
		builder.setAutoCancel(autoCancel);
		
		if (pendingIntent != null) {
			setPendingIntent(pendingIntent, text, title);
		}
		
		builder.addAction(R.drawable.ic_touch_app, "DISMISS", getDismissedIntent(notificationID, context));
		
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
	private static void setPendingIntent(PendingIntent pendingIntent, String text, String title) {
		builder.setContentTitle(title);
		builder.setContentText(text);
		builder.setAutoCancel(true);
		builder.addAction(R.drawable.ic_touch_app, "VIEW", pendingIntent);
	}
	
	/**
	 * @param notificationID
	 * @param context
	 *
	 * @return
	 */
	private static PendingIntent getDismissedIntent(int notificationID, Context context) {
		String NOTIFICATION_ID = "NOTIFICATION ID";
		Intent intent = new Intent(context, context.getClass());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.putExtra(NOTIFICATION_ID, notificationID);
		return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
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