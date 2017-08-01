package uk.panasys.phonehabits.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import uk.panasys.phonehabits.receivers.UserPresentReceiver;


public class CountService extends Service {

    private BroadcastReceiver userPresentReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        userPresentReceiver = new UserPresentReceiver();
        registerReceiver(userPresentReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(userPresentReceiver);
    }

}