package com.example.administrator.smsmanage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("AlarmReceiver","shoudao");
        context.startService(new Intent(context, SmsService.class));
        context.startService(new Intent(context,AlarmService.class));

    }
}
