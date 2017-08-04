package uk.panasys.phonehabits.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import uk.panasys.phonehabits.activities.MainActivity;

public class UserPresentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_USER_PRESENT.equals(action)) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = defaultSharedPreferences.edit();
            editor.putString(MainActivity.SCREEN_ON_COUNTER, String.valueOf(Integer.valueOf(defaultSharedPreferences.getString(MainActivity.SCREEN_ON_COUNTER, "0")) + 1));
            editor.apply();
        }
    }
}
