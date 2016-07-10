package com.example.administrator.smsmanage;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button button_AutRelay;
    private Button button_Forward;
    private static final int ALARM_TIME=60*1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_AutRelay=(Button)findViewById(R.id.buttonRelay);
        button_Forward=(Button)findViewById(R.id.buttonForward);
        button_AutRelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,AutoRelayActivity.class);
                startActivity(intent);
            }
        });
        button_Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ForWardActivity.class);
                startActivity(intent);
            }
        });
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, SmsService.class);
        startService(intent);
        startService(new Intent(MainActivity.this,AlarmService.class));
    }


    /**
     * 为了守护进程 重复发生广播启动服务
     * */
    public  static void setAlarm(Context context){
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context,AlarmReceiver.class);
        int requestCode=0;
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        long triggerAtTime= SystemClock.elapsedRealtime();
        Log.i("shoudaoshijian","shijian");
        alarmManager.setRepeating(AlarmManager.RTC,System.currentTimeMillis(),ALARM_TIME,pendingIntent);

    }



}
