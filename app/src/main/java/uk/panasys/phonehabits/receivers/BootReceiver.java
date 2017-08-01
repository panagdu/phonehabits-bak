package uk.panasys.phonehabits.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import uk.panasys.phonehabits.services.CountService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent countService = new Intent(context, CountService.class);
        context.startService(countService);
    }
}
