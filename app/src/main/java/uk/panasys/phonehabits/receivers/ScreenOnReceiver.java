package uk.panasys.phonehabits.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import java.util.Calendar;

import uk.panasys.phonehabits.R;

import static android.content.Context.MODE_PRIVATE;

public class ScreenOnReceiver extends BroadcastReceiver {
    private int screenOnCounter = 0;
    private TextView screenOnCounterText;

    public ScreenOnReceiver(TextView screenOnCounterText) {
        this.screenOnCounterText = screenOnCounterText;
        this.screenOnCounter = Integer.parseInt(screenOnCounterText.getText().toString());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long [] pattern = {0, 100, 1000};
        int mNotificationId = 1;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setVibrate(defaultSharedPreferences.getBoolean("pref_notifications_vibrate", false) ?  pattern : null)
                        .setSmallIcon(R.drawable.ic_info_black_24dp)
                        .setContentTitle("Relax!")
                        .setContentText("You've checked your phone 100 times today!");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, mBuilder.build());

        Boolean personalizedGreeting = defaultSharedPreferences.getBoolean("pref_reset_every_day", false);

        if (personalizedGreeting) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("count", MODE_PRIVATE);
            String day = sharedPreferences.getString("DAY", "0");

            Calendar cal = Calendar.getInstance();
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            String dayOfMonthStr = String.valueOf(dayOfMonth);

            if (!dayOfMonthStr.equals(day)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("DAY", dayOfMonthStr);
                screenOnCounterText.setText("1");
                screenOnCounter = 1;
                editor.apply();
            }
        }
        screenOnCounterText.setText(String.valueOf(screenOnCounter++));
    }
}