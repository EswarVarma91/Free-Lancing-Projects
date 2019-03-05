package es.hol.iamfuture.lastlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends AppCompatActivity {

    private SimpleLocation simpleLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleLocation=new SimpleLocation(this);

        // if we can't access the location yet
        if (!simpleLocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

    }
}
