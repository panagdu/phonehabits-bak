package uk.panasys.phonehabits.activities;

import android.content.Intent;
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

import java.text.MessageFormat;

import uk.panasys.phonehabits.R;
import uk.panasys.phonehabits.listeners.NavigationViewListener;
import uk.panasys.phonehabits.services.CountService;

import static uk.panasys.phonehabits.utils.ServicesUtils.isServiceRunning;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.getBooleanPreference;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.getIntegerPreference;
import static uk.panasys.phonehabits.utils.SharedPreferencesUtils.getStringPreference;

public class MainActivity extends AppCompatActivity {

    public static final String SCREEN_ON_COUNTER = "SCREEN_ON_COUNTER";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TextView screenOnCounterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //init fields
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        screenOnCounterText = findViewById(R.id.screenOnCounterText);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // init state of the app
        Boolean personalizedGreeting = getBooleanPreference(this, "pref_personalized_greeting", false);
        String displayName = getStringPreference(this, "pref_display_name", "");

        if (personalizedGreeting) {
            TextView personalizedGreetingText = findViewById(R.id.greetingTextView);
            personalizedGreetingText.setText(MessageFormat.format("Hi {0}, {1}", displayName, personalizedGreetingText.getText()));
        }


        screenOnCounterText.setText(String.valueOf(getIntegerPreference(this, SCREEN_ON_COUNTER, 0)));


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationViewListener(this));

        if (!isServiceRunning(this, CountService.class)) {
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }


    @Override
    protected void onResume() {
        super.onResume();
        screenOnCounterText.setText(String.valueOf(getIntegerPreference(this, SCREEN_ON_COUNTER, 0)));
    }
}
