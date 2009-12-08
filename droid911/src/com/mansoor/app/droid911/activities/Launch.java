package com.mansoor.app.droid911.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.mansoor.app.droid911.util.C;

/**
 * Created by IntelliJ IDEA.
 * User: Mansoor
 * Date: Dec 7, 2009
 * Time: 7:53:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Launch extends MapActivity implements View.OnClickListener
{
    private MapView mapView;
    private MapController mapController;
    private Location knownLocation;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);
        initMapView();
        initMyLocation();
        final Button newLocation = (Button) findViewById(R.id.newlocation);
        newLocation.setOnClickListener(this);

        final Button currentLocation = (Button) findViewById(R.id.currentlocation);
        currentLocation.setOnClickListener(this);
    }


    @Override
    protected boolean isRouteDisplayed()
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void initMapView()
    {
        mapView = (MapView) findViewById(R.id.map_view);
        mapController = mapView.getController();
    }

    /**
     * Start tracking the position on the map.
     */
    private void initMyLocation()
    {
        final MyLocationOverlay overlay = new MyLocationOverlay(this, mapView);
        overlay.enableMyLocation();
        overlay.runOnFirstFix(new MyRunnable(overlay));
        mapView.getOverlays().add(overlay);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.currentlocation:
                handleCurrentLocation(view);
                break;
            case R.id.newlocation:
                handleNewLocation(view);
                break;

        }
    }

    private void handleNewLocation(View view)
    {
        Intent i = new Intent(view.getContext(), EnterAddress.class);
         i.putExtra(C.DISPLAY_MESSAGE, false);
        startActivity(i);
    }

    private void handleCurrentLocation(View view)
    {
        if (getLastLocation())
        {

            final double latitude = knownLocation.getLatitude();
            final double longitude = knownLocation.getLongitude();
            Intent i = new Intent(view.getContext(), POIList.class);
            i.putExtra(C.LONGITUDE, longitude);
            i.putExtra(C.LATITUDE, latitude);
            i.putExtra(C.STATUS, C.CURRENT);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(view.getContext(), EnterAddress.class);
            i.putExtra(C.DISPLAY_MESSAGE, true);
            startActivity(i);
        }
    }

    private Boolean getLastLocation()
    {
        Boolean result = false;

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (bestProvider != null)
        {
            knownLocation = locationManager.getLastKnownLocation(bestProvider);
            if (knownLocation != null)
            {
                result = true;
            }
        }


        return result;
    }


    private class MyRunnable implements Runnable
    {
        private final MyLocationOverlay overlay;

        public MyRunnable(MyLocationOverlay overlay)
        {
            this.overlay = overlay;
        }

        public void run()
        {
            // Zoom in to current location
            mapController.setZoom(8);
            mapController.animateTo(overlay.getMyLocation());
        }
    }
}