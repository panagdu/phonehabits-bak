package uk.panasys.phonehabits.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import uk.panasys.phonehabits.R;
import uk.panasys.phonehabits.listeners.NavigationViewListener;
import uk.panasys.phonehabits.receivers.ScreenOnReceiver;
import uk.panasys.phonehabits.services.CountService;

public class MainActivity extends AppCompatActivity {

    public static final String SCREEN_ON_COUNTER = "SCREEN_ON_COUNTER";
    public static final String COUNT_FILE = "count";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TextView screenOnCounterText;
    private ScreenOnReceiver screenOnReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        screenOnCounterText = findViewById(R.id.screenOnCounterText);
        SharedPreferences sharedPreferences = getSharedPreferences(COUNT_FILE, MODE_PRIVATE);
        screenOnCounterText.setText(sharedPreferences.getString(SCREEN_ON_COUNTER, "0"));


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationViewListener(this));

        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenOnReceiver = new ScreenOnReceiver(screenOnCounterText);
        registerReceiver(screenOnReceiver, screenStateFilter);


        if (!isMyServiceRunning(CountService.class)) {
            Intent countService = new Intent(this, CountService.class);
            startService(countService);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent serviceIntent = new Intent(this, CountService.class);
            startService(serviceIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("count", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCREEN_ON_COUNTER, screenOnCounterText.getText().toString());
        editor.apply();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(screenOnReceiver);
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }
}
