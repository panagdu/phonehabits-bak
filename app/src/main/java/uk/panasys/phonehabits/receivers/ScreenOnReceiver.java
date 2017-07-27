package uk.panasys.phonehabits.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class ScreenOnReceiver extends BroadcastReceiver {
    private Integer clicksCounter = 0;
    private TextView clicksCounterText;

    public ScreenOnReceiver(TextView clicksCounterText) {
        this.clicksCounterText = clicksCounterText;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        clicksCounter++;
        clicksCounterText.setText(String.valueOf(clicksCounter));
        //Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
    }
}