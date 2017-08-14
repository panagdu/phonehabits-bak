package uk.panasys.phonehabits.listeners;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import uk.panasys.phonehabits.R;
import uk.panasys.phonehabits.activities.MainActivity;
import uk.panasys.phonehabits.activities.SettingsActivity;

public class NavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivity activity;

    public NavigationViewListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_checks) {
            Snackbar.make(activity.getDrawer(), "Wow, this goes at the bottom!", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else if (id == R.id.nav_settings) {
            Intent settingsIntent = new Intent(activity.getApplicationContext(), SettingsActivity.class);
            activity.getApplicationContext().startActivity(settingsIntent);
            return true;
        } else if (id == R.id.nav_exit) {
            activity.finish();
        }

        activity.getDrawer().closeDrawer(GravityCompat.START);
        return true;
    }
}
