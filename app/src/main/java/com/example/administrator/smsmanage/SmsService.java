package com.example.administrator.smsmanage;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsService extends Service {

    private static final String ACTION="android.provider.Telephony.SMS_RECEIVED";

    private String mobile;

    private String content;

    private SharedPreferences preferences;

    private String replyNumber;

    private String replyContent;

    private String forwardNumber;

    public SmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //启动时钟 守护进程
        MainActivity.setAlarm(getApplicationContext());
        Log.i("SMSservice", "shoudaocreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
                super.onStartCommand(intent, flags, startId);
        if (preferences==null){
            preferences=getSharedPreferences("smsdata",MODE_PRIVATE);
        }
        registerListerner();

        Log.i("SMSservice", "shoudaoqidong");
        return START_STICKY;
    }

    private void  registerListerner(){
        IntentFilter intentFilter = new IntentFilter( "android.provider.Telephony.SMS_RECEIVED" );
        intentFilter.setPriority(100000);
        registerReceiver(SMSReceiver, intentFilter);
    }

    private BroadcastReceiver SMSReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(ACTION)) {
                Log.i("SMSservice", "shoudao");
                //获得短信对象
                Object[] pduses = (Object[]) intent.getExtras().get("pdus");
//                receiveSMSBroadcas(pduses);
                querySMSdata();
            }

        }
    };

    /***
     * 接受短信广播
     * 处理短信
     */
    private void  receiveSMSBroadcas(Object[] pduses){
        replyNumber=preferences.getString("phoneNumber","");
        replyContent=preferences.getString("replyContent","");
        forwardNumber=preferences.getString("forwardNumber","");
        if (pduses != null) {
            for (Object pdus : pduses) {
                byte[] pdusmessage = (byte[]) pdus;
                SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);
                mobile = sms.getOriginatingAddress();//发送短信的手机号码
                content = sms.getMessageBody(); //短信内容
                Log.i("SMSservice", "shoudao" + mobile + content);
                //回复短信
                if (!replyNumber.equals("")){
                    if (mobile.contains(replyNumber)){
                        sendSMS(mobile, replyContent);
                        Log.i("SMSservice", "shoudao" + mobile + replyContent);
                    }
                }
                //转发短信
                if (!forwardNumber.equals("")){
                    sendSMS(forwardNumber, content);
                    Log.i("SMSservice", "shoudao" + forwardNumber +content);
                }
            }
        }
    }


    private  void querySMSdata(){
        ContentResolver resolver=getContentResolver();
        resolver.registerContentObserver(Uri.parse("content://sms/inbox"),true,new SMSObserver(new Handler()));
        Log.i("SMSservice", "shoudaoDATA");
    }

    public class SMSObserver extends ContentObserver {

        public SMSObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            replyNumber=preferences.getString("phoneNumber","");
            replyContent=preferences.getString("replyContent","");
            forwardNumber=preferences.getString("forwardNumber","");
            Log.i("SMSservice", "shoudaoINBOX");
            String number;
            String contents;
            Cursor cursor=getContentResolver().query(Uri.parse("content://sms/inbox"),new String[]{"address ","body"},"address=?",new String[]{("+86"+replyNumber)},"date desc");
            if (cursor!=null&&cursor.getCount()>0){
                if (cursor.moveToFirst()){
                    number=cursor.getString(0);
                    contents=cursor.getString(1);
                    if (number.contains(replyNumber)){
                        sendSMS(number, replyContent);
                        Log.i("SMSservice", "shoudao" + number + replyContent);
                    }
                    if (!forwardNumber.equals("")){
                        sendSMS(forwardNumber,contents);
                        Log.i("SMSservice", "shoudao" + forwardNumber + contents);
                    }
                }
                cursor.close();
            }
        }

    }


    private void sendSMS(String number,String contents){
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(number,null,contents,null,null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent(this, AlarmReceiver.class));
        getContentResolver().unregisterContentObserver(new SMSObserver(new Handler()));
        Log.i("SMSservice","shoudao广播");
    }
}
