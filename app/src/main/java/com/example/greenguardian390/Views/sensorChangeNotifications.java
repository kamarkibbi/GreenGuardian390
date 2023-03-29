package com.example.greenguardian390.Views;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.greenguardian390.Models.Plant;
import com.example.greenguardian390.Models.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.greenguardian390.R;

public class sensorChangeNotifications extends Service {

    private static final String CHANNEL_ID = "The Green Guardian";
    private NotificationManager mNotificationManager;

    private String notificationMessage;

    private UserProfile currentUser;

    DatabaseReference mDatabase;

    public sensorChangeNotifications() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //get data here and do threshold here, call notification in login page

        mDatabase = FirebaseDatabase.getInstance().getReference("SenData");

        //UserProfile currentuser=(UserProfile) getIntent().getSerializableExtra("currentProfile");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot d :snapshot.getChildren())
                {
                    if (d.getKey().toLowerCase().contains("moisture"))
                    {
                        if(currentUser != null && currentUser.getUserPlants().size()>0)
                        {
                            for (Plant p : currentUser.getUserPlants())
                            {
                                if((Long)d.getValue()>=(p.getActualSoilMoisture()+5))
                                {
                                    notificationMessage="Your soil moisture is too high, fix it!";
                                } else if ((Long)d.getValue()<=(p.getActualSoilMoisture())+5) {

                                    notificationMessage="Your soil moisture is too low, fix it!";

                                }
                            }
                        }

                    }

                    if(d.getKey().toLowerCase().contains("temperature"))
                    {
                        if(currentUser != null && currentUser.getUserPlants().size()>0)
                        {
                            for (Plant p : currentUser.getUserPlants())
                            {
                                if((Long)d.getValue()>=(p.getActualTemp()+5))
                                {
                                    notificationMessage="Your temperature is too high, fix it!";
                                } else if ((Long)d.getValue()<=(p.getActualTemp()+5)) {

                                    notificationMessage="Your temperature is too low, fix it!";

                                }
                            }
                        }

                        //stopService(new Intent(PlantPage.this, sensorChangeNotifications.class));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "The Green Guardian", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    // Create notification builder
    private NotificationCompat.Builder createNotificationBuilder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.bell)
                .setContentTitle("Check your plant")
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder;
    }

    private void showNotification() {
        NotificationCompat.Builder builder = createNotificationBuilder();
        mNotificationManager.notify(1, builder.build());
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent !=null && intent.getExtras() != null)
        {
            Bundle bundle = intent.getExtras();
            currentUser=(UserProfile) bundle.getSerializable("currentProfile");
            if (currentUser != null)
            {
                //show notification
                showNotification();
            }
        }


        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //thread.sleep for 1 min (google how to for service)
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }


}