package com.example.shopbee.noti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            SharedPreferences preferences = context.getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isNotificationForSalesScheduled", false);
            editor.apply();

            NotificationScheduler.scheduleDailyNotification(context, 15, 0);
            NotificationScheduler.scheduleDailyNotification(context, 18, 0);
            NotificationScheduler.scheduleDailyNotification(context, 21, 0);

            editor.putBoolean("isNotificationScheduled", true);
            editor.apply();
        }
    }
}