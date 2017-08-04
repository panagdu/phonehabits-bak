package uk.panasys.phonehabits.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import uk.panasys.phonehabits.activities.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class UserPresentReceiver extends BroadcastReceiver {

    public static final String SCREEN_COUNT_FILE = "count";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_USER_PRESENT.equals(action)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SCREEN_COUNT_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(MainActivity.SCREEN_ON_COUNTER, String.valueOf(Integer.valueOf(sharedPreferences.getString(MainActivity.SCREEN_ON_COUNTER, "0")) + 1));
            editor.apply();
        }
        else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.e("tag", "screen off");
        }
    }
}
