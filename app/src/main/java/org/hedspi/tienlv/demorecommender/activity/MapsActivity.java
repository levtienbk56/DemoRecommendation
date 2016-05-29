package org.hedspi.tienlv.demorecommender.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.hedspi.tienlv.demorecommender.R;
import org.hedspi.tienlv.demorecommender.model.Ads;
import org.hedspi.tienlv.demorecommender.model.AdsGenerator;
import org.hedspi.tienlv.demorecommender.model.Itemset;
import org.hedspi.tienlv.demorecommender.model.Pattern;
import org.hedspi.tienlv.demorecommender.model.PatternDB;
import org.hedspi.tienlv.demorecommender.modul.geotag.NearbyPlaceAPI;
import org.hedspi.tienlv.demorecommender.modul.patternmatching.PatternMatching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsActivity extends FragmentActivity implements View.OnClickListener{

    PatternDB patternDB;
    List<Ads> listAds;
    Pattern userSequence;
    List<String> curLabels;
    private GoogleMap mMap;
    LocationManager locationManager;
    Location location;
    double latitude, longitude;
    TextView tvAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tvAds = (TextView) findViewById(R.id.ads);
        // track gps
        // initTrackService();

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);

    }

    private void testMinhChauMarket(){
        MarkerOptions marker = new MarkerOptions().position(new LatLng(21.005063,105.8463869));

        // adding marker
        mMap.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(21.005063,105.8463869)).zoom(12).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * get last location
     */
    private void getLastLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation
                (LocationManager.PASSIVE_PROVIDER);
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Toast.makeText(this, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
            // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));

            // adding marker
            mMap.addMarker(marker);
        }
    }

    /**
     * start service track gps
     */
    private void initTrackService() {
        // Start receiver with the name StartupReceiver_Manual_Start
        // @see AndroidManifest.xml file
        getBaseContext().getApplicationContext().sendBroadcast(
                new Intent("StartupReceiver_Manual_Start"));
    }

    @Override
    public void onClick(View v) {
        getLastLocation();
        detectAds();
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class GeotagTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... args) {
            double latitude = Double.parseDouble(args[0]);
            double longitude = Double.parseDouble(args[1]);

            // params comes from the execute() call: params[0] is the url.
            try {
                // geotag get labels
                curLabels = NearbyPlaceAPI.getLabels(latitude, longitude);

                return curLabels;
            } catch (IOException e) {
                return new ArrayList<>();
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(List<String> result) {
            String str = "";
            for (String rs : result) {
                str += rs + " ";
            }
            // show current labels
            // tv_curLabels.setText(str);

            // insert labels of current location into user pattern
            userSequence.itemsets.add(new Itemset(result));

            // detect pattern, and recommendation
            detectAds();
        }
    }

    /**
     * load temporary data for test
     */
    private void loadUserPattern() {
        // create user sequence (as a pattern)
        userSequence = new Pattern();
        List<String> s1 = new ArrayList<>();
        s1.add("food");
        s1.add("store");
        s1.add("restaurant");

        List<String> s2 = new ArrayList<>();
        s2.add("school");
        s2.add("store");

        userSequence.itemsets.add(new Itemset(s1));
        userSequence.itemsets.add(new Itemset(s2));
        Log.d("User sequence", userSequence.toString());
    }


    /**
     * Pattern matching
     * we have {patterns, user's sequence, ads}.
     * find ads that match patterns and user sequence
     */
    private void detectAds() {
        // load pattern DB
        patternDB = new PatternDB(this);
        patternDB.loadPatternDB();

        // load user sequence
        loadUserPattern();

        // load ads
        listAds = new AdsGenerator().generate();

        // find  pattern matching
        PatternMatching pm = new PatternMatching(userSequence, patternDB);

        // browse follow sorted patternMatching, then find label in listAds
        boolean flag = false;


        // found a pattern, those patterns are ranked my count point
        for(PatternMatching.Triplet<Pattern, Integer, Integer> patternRanked : pm.match()) {
            Pattern pattern = patternRanked.getA();
            int index = patternRanked.getC();

            int i;
            for(i = index + 1; i<pattern.itemsets.size(); i++){
                Itemset foundLabels = pattern.itemsets.get(i);

                // now find ads
                for (Ads ads : listAds) {
                    Itemset adsItemset = new Itemset(ads.getLabels());
                    if (!Collections.disjoint(adsItemset.items, foundLabels.items)) {
                        Log.d("DETECT ADS", ads.getContent());
                        tvAds.setText(ads.getContent());
                        flag = true;
                        break;
                    }
                }
                if(flag == true){
                    break;      // find only 1 ads
                }
            }
            if(flag == true){
                break;          // find only 1 ads
            }
        }
    }

}
