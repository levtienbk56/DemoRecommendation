package org.hedspi.tienlv.demorecommender.modul.trackergps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.hedspi.tienlv.demorecommender.helper.file.FileHelper;
import org.hedspi.tienlv.demorecommender.helper.file.FileHelperImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tienlv.hust on 3/13/2016.
 */
public class GPSTrackingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("GPS_TRACK_RECEIVER", "on receiver");

        GPSTracker tracker = GPSTracker.getInstance(context);
        if(tracker.getLocation() != null){
            double latitude = tracker.getLatitude();
            double longitude = tracker.getLongitude();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(Calendar.getInstance().getTime());
            String str = "0001," + date + "," +longitude + "," + latitude +"\n";

            FileHelper fileHelper =  new FileHelperImpl(context);
            fileHelper.writeToFile(str);
            Log.d("GPS Track Broadcast", str);
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            // @see tracker.showSettingsAlert();
            Log.d("LogActivity", "GPS not enabled or Internet not available");
        }
    }
}
