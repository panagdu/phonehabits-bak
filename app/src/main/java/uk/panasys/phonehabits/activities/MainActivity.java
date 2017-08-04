package uk.panasys.phonehabits.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.MessageFormat;

import uk.panasys.phonehabits.R;
import uk.panasys.phonehabits.listeners.NavigationViewListener;
import uk.panasys.phonehabits.receivers.ScreenOnReceiver;
import uk.panasys.phonehabits.services.CountService;
import uk.panasys.phonehabits.utils.ServicesUtils;

public class MainActivity extends AppCompatActivity {

    public static final String SCREEN_ON_COUNTER = "SCREEN_ON_COUNTER";
    private ServicesUtils servicesUtils;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TextView screenOnCounterText;
    private ScreenOnReceiver screenOnReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        servicesUtils = new ServicesUtils();

        setSupportActionBar(toolbar);


//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Boolean personalizedGreeting =defaultSharedPreferences.getBoolean("pref_personalized_greeting", false);
        String displayName = defaultSharedPreferences.getString("pref_display_name", "");

        if(personalizedGreeting){
            TextView personalizedGreetingText = findViewById(R.id.greetingTextView);
            personalizedGreetingText.setText(MessageFormat.format("Hi {0}, {1}", displayName, personalizedGreetingText.getText()));
        }

        screenOnCounterText = findViewById(R.id.screenOnCounterText);
        screenOnCounterText.setText(defaultSharedPreferences.getString(SCREEN_ON_COUNTER, "0"));


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationViewListener(this));

        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenOnReceiver = new ScreenOnReceiver(screenOnCounterText);
        registerReceiver(screenOnReceiver, screenStateFilter);


        if (!servicesUtils.isServiceRunning(this, CountService.class)) {
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
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
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



    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(screenOnReceiver);
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }
}
