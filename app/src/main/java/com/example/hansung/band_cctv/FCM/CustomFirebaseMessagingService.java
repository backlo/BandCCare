package com.example.hansung.band_cctv.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit2.RetroClient2;
import com.example.hansung.band_cctv.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    RetroClient2 retroClient2;
    String title;
    String body;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        retroClient2 = RetroClient2.getInstance().createBaseApi2();

        Map<String, String> pushDataMap = remoteMessage.getData();
        Log.e("fcm get data",""+pushDataMap.get("touch_data"));

        HashMap<String, Object> alarmmap = new HashMap<>();
        alarmmap.put("alarm","alarm");
        retroClient2.Send_Alarm(alarmmap, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.e("alarm","123123123!!!!");

            }
            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.e("alarm","알람");
            }

            @Override
            public void onFailure(int code) {
                Log.e("alarm","okokokok!!!!");

            }
        });


        sendNotification(pushDataMap);
    }

    private void sendNotification(Map<String, String> dataMap) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if(dataMap != null){
            if(dataMap.get("check").equals("notouch")){
                title = "BandCCare";
                body = "밴드 미착용 감지";
        }
        if(dataMap.get("check").equals("heart")){
                title = "BandCCare";
                body = "심박수 이상 감지";
            }
        }
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this,"0")
                .setSmallIcon(R.drawable.sos)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{1000, 1000})
                .setLights(Color.WHITE, 1500, 1500)
                .setContentIntent(contentIntent);

        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(1 /* ID of notification */, nBuilder.build());
    }
}
