package org.hedspi.tienlv.demorecommender.modul.trackergps;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by tienlv.hust on 3/13/2016.
 */
public class StartupReceiver extends BroadcastReceiver {
    final int STARTUP_RECEIVER_ID = 12345;
    final int TIME_CYCLE = 1000*60*1;    // 1 minutes

    /**
     * this BroadCast will be start when device Boot_complete
     * then looping track GPS
     *
     * @see GPSTrackingReceiver
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(context, GPSTrackingReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, STARTUP_RECEIVER_ID, i, 0);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), TIME_CYCLE, pendingIntent);
    }
}
