package uk.panasys.phonehabits.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import uk.panasys.phonehabits.receivers.ScreenOnReceiver;


public class CountService extends Service {

    private BroadcastReceiver screenOnReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenOnReceiver = new ScreenOnReceiver();
        registerReceiver(screenOnReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenOnReceiver);
    }

}