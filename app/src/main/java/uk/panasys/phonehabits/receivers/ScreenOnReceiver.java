package uk.panasys.phonehabits.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class ScreenOnReceiver extends BroadcastReceiver {
    private int screenOnCounter = 0;
    private TextView screenOnCounterText;

    public ScreenOnReceiver(TextView screenOnCounterText) {
        this.screenOnCounterText = screenOnCounterText;
        this.screenOnCounter = Integer.parseInt(screenOnCounterText.getText().toString());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        screenOnCounterText.setText(String.valueOf(screenOnCounter++));
    }
}