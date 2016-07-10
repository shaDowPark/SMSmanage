package com.example.administrator.smsmanage;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        //启动时钟 守护进程
//        MainActivity.setAlarm(getApplicationContext());
        Log.i("AlarmService","shoudao");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        sendBroadcast(new Intent(this, AlarmReceiver.class));
    }
}
