package bft.fishtagsapp;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GPSActivity extends AppCompatActivity implements LocationListener{

    private TextView lat_tv;
    private TextView long_tv;
    private LocationManager locationManager;
    private Location location;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        lat_tv = (TextView) findViewById(R.id.lat_tv);
        long_tv = (TextView) findViewById(R.id.long_tv);

        Button gps_btn = (Button) findViewById(R.id.gps_btn);
        if(gps_btn != null){
            Log.i("Button exists","Yay");
            gps_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getGPS();
                }
            });
        }else{
            Log.i("Button doesn't exist","Boo");
        }

    }
    @Override
    public void onProviderEnabled(String provider) {
        //indicate provider enabled
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onProviderDisabled(String provider) {
        //indicate provider enabled
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        //report new location to textViews
        Log.i("LATITUDE",String.valueOf(location.getLatitude()));
        Log.i("LONGITUDE",String.valueOf(location.getLongitude()));

        this.location = location;
        lat_tv.setText(String.valueOf(location.getLatitude()));
        long_tv.setText(String.valueOf(location.getLongitude()));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //nothing yet
    }

    public void enableGPS(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Log.i("ENABLED","FALSE");
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        Log.i("ENABLED","TRUE");
        //hopefully user will enable GPS here
    }

    public void getGPS() {
        enableGPS(); //in case not enabled
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        // Add Error Checking to prevent app failure
        Log.i("PROVIDER : ", provider);
        try {
            //to be safe
            locationManager.requestLocationUpdates(provider, 0, 0, this);
            location = locationManager.getLastKnownLocation(provider);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
        // Initialize the location fields
        Log.i("LOCATION : ", String.valueOf(location));

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            lat_tv.setText("Location not available");
            long_tv.setText("Location not available");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}