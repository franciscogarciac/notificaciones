package com.example.appnotificaciones;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class RecibirNotificacion extends FirebaseMessagingService {


    public static final String TAG = "Notificaciones";


    public void RecibirNotificacion (){

    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        String form = remoteMessage.getFrom();
        Log.d(TAG, "Mensaje Recibido: "+ form);
        Map<String, String> data = null;
        if (remoteMessage != null){
            //Log.d(TAG, "Notificación: " + remoteMessage.getNotification().getTitle());
            String r = remoteMessage.getData().get("message");
            Log.d(TAG, "Mensaje: "+ r);
            data = remoteMessage.getData();
            ;
        }
        String logro = "si";
           // message, here is where that should be initiated. See sendNotification method below.
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(String message) {


        Intent intent =  new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "my_channel_id";
        CharSequence channelName = "My Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 2000});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        int notifyId = 1;

        Notification notification = new Notification.Builder(this)
                .setContentTitle("My Message")
                .setContentText("My test message!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(channelId)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(notifyId, notification);


    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }
}
