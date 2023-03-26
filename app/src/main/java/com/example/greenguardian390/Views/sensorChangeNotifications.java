package com.example.greenguardian390.Views;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.greenguardian390.R;

public class sensorChangeNotifications extends Service {

    private static final String CHANNEL_ID = "my_channel_id";
    private NotificationManager mNotificationManager;

    public sensorChangeNotifications() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    // Create notification builder
    private NotificationCompat.Builder createNotificationBuilder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.bell)
                .setContentTitle("My Notification Title")
                .setContentText("My Notification Text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder;
    }

    private void showNotification() {
        NotificationCompat.Builder builder = createNotificationBuilder();
        mNotificationManager.notify(0, builder.build());
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        // Show notification
        showNotification();

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}