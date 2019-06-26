package com.merrill.msg3;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS},1);

        getContentResolver().registerContentObserver(Uri.parse("content://sms"),true,new SmsObserver(new Handler()));
    }

    //内容观察者

    private class SmsObserver extends ContentObserver {

        public SmsObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onChange(boolean selfChange) {
            // TODO Auto-generated method stub
            //查询发送箱中的短信（正在发送中的短信放在发送箱中）
            Cursor cursor = getContentResolver().query(
                    Uri.parse("content://sms/inbox"), null, null, null, null);
            while(cursor.moveToNext()){
                StringBuilder sb = new StringBuilder();
                sb.append("address="+cursor.getString(cursor.getColumnIndex("address")));
                sb.append(", body="+cursor.getString(cursor.getColumnIndex("body")));
                sb.append(", date="+cursor.getString(cursor.getColumnIndex("date")));
                Log.i("SMsObserver", sb.toString());
            }
            cursor.close();
            super.onChange(selfChange);
        }
    }

}
