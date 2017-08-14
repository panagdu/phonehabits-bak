package uk.panasys.phonehabits.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

import uk.panasys.phonehabits.R;

import static uk.panasys.phonehabits.activities.MainActivity.SCREEN_ON_COUNTER;

public class ScreenOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Integer checkPhoneCounter = sharedPreferences.getInt(SCREEN_ON_COUNTER, 0);

        sendNotification(context, sharedPreferences);
        checkPhoneCounter = resetCounterIfNeeded(sharedPreferences, checkPhoneCounter);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SCREEN_ON_COUNTER, ++checkPhoneCounter);
        editor.apply();
    }

    private Integer resetCounterIfNeeded(SharedPreferences sharedPreferences, Integer checkPhoneCounter) {
        Boolean resetEveryDay = sharedPreferences.getBoolean("pref_reset_every_day", false);

        if (resetEveryDay) {
            String day = sharedPreferences.getString("DAY", "0");

            Calendar cal = Calendar.getInstance();
            Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            String dayOfMonthStr = String.valueOf(dayOfMonth);

            if (!dayOfMonthStr.equals(day)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("DAY", dayOfMonthStr);
                checkPhoneCounter = 0;
                editor.apply();
            }
        }
        return checkPhoneCounter;
    }

    private void sendNotification(Context context, SharedPreferences sharedPreferences) {
        long[] pattern = {0, 100, 1000};
        int mNotificationId = 1;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setVibrate(sharedPreferences.getBoolean("pref_notifications_vibrate", false) ? pattern : null)
                        .setSound(sharedPreferences.getBoolean("pref_notifications_vibrate", false) ? Uri.parse(sharedPreferences.getString("pref_notifications_ringtone", "")) : null)
                        .setSmallIcon(R.drawable.ic_smartphone_black_24dp)
                        .setContentTitle("Relax!")
                        .setContentText("You've checked your phone 100 times today!");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, mBuilder.build());
    }
}