package com.example.owner.project_final;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // datapayload로 보내면 실행된다.

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            if (true) {
                // scheduleJob();

            } else {
                handleNow();
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            sendNotification(remoteMessage.getNotification().getBody());
        }


  /*
    PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

    PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");


    wakeLock.acquire(3000);

    String title = remoteMessage.getData().get("title");
    String body = remoteMessage.getData().get("content");

    sendNotification(title, body);

     */


    }

   /*

    private  void scheduleJob() {

       Log.d(TAG, "이것에 대해서는 나중에 알아보자.");
    }



     */


    private void handleNow() {
        Log.d(TAG, "10초이내 처리됨");
    }

    private void sendNotification(String messageBody) {


        Intent intent = new Intent(getApplicationContext(), Tab1Activity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);

        Uri defaultsSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("FCM Message").setContentText(messageBody).setAutoCancel(true).setSound(defaultsSoundUri)
                // .setVibrate(new long[]{1000, 1000})
                // .setLights(Color.BLUE, 1,1)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            {
                String channelName = getString(R.string.default_notification_channel_id);

                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);

            }

            notificationManager.notify(0, notificationBuilder.build());

        }
    }
}


