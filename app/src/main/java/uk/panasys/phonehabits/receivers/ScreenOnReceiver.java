package uk.panasys.phonehabits.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import uk.panasys.phonehabits.R;

public class ScreenOnReceiver extends BroadcastReceiver {
    private int screenOnCounter = 0;
    private TextView screenOnCounterText;

    public ScreenOnReceiver(TextView screenOnCounterText) {
        this.screenOnCounterText = screenOnCounterText;
        this.screenOnCounter = Integer.parseInt(screenOnCounterText.getText().toString());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int mNotificationId = 001;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_info_black_24dp)
                        .setContentTitle("Relax!")
                        .setContentText("You've checked your phone 100 times today!");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, mBuilder.build());

        screenOnCounterText.setText(String.valueOf(screenOnCounter++));
    }
}