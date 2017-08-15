package uk.panasys.phonehabits.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

import uk.panasys.phonehabits.R;

import static uk.panasys.phonehabits.activities.MainActivity.SCREEN_ON_COUNTER;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.getBooleanPreference;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.getIntegerPreference;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.getStringPreference;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.setBooleanPreference;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.setIntegerPreference;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.setStringPreference;

public class ScreenOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Integer checkPhoneCounter = getIntegerPreference(context, SCREEN_ON_COUNTER, 0);

        sendNotification(context);
        checkPhoneCounter = resetCounterIfNeeded(context, checkPhoneCounter);
        setIntegerPreference(context, SCREEN_ON_COUNTER, ++checkPhoneCounter);
    }

    private Integer resetCounterIfNeeded(Context context, Integer checkPhoneCounter) {
        Boolean resetEveryDay = getBooleanPreference(context, "pref_reset_every_day", false);

        if (resetEveryDay) {
            String day = getStringPreference(context, "DAY", "0");

            Calendar cal = Calendar.getInstance();
            Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            String dayOfMonthStr = String.valueOf(dayOfMonth);

            if (!dayOfMonthStr.equals(day)) {
                setStringPreference(context, "DAY", dayOfMonthStr);
                checkPhoneCounter = 0;
                setBooleanPreference(context,"pref_notification_sent_today", false);
            }
        }
        return checkPhoneCounter;
    }

    private void sendNotification(Context context) {
        Boolean sentToday = getBooleanPreference(context, "pref_notification_sent_today", false);
        if (!sentToday){
            long[] pattern = {0, 100, 1000};
            int mNotificationId = 1;
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setVibrate(getBooleanPreference(context, "pref_notifications_vibrate", false) ? pattern : null)
                            .setSound(getBooleanPreference(context, "pref_notifications_vibrate", false) ? Uri.parse(getStringPreference(context, "pref_notifications_ringtone", "")) : null)
                            .setSmallIcon(R.drawable.ic_smartphone_black_24dp)
                            .setContentTitle("Relax!")
                            .setContentText("You've checked your phone 50 times today!");

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(mNotificationId, mBuilder.build());
            setBooleanPreference(context,"pref_notification_sent_today", true);
        }
    }
}