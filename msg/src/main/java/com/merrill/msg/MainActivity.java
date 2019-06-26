package com.merrill.msg;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS},1);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, new SmsObserver(new Handler()));
        textView = findViewById(R.id.textview);
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.查出所有的短信
                Uri uri = Uri.parse("content://sms/");
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver
                        .query(uri, new String[]{"_id", "address", "date", "type", "body"},
                        null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    List<SmsInfo> smsList = new ArrayList<>();
                    SmsInfo sms;
                    while (cursor.moveToNext()) {
                        sms = new SmsInfo();
                        sms.setId(cursor.getInt(0));
                        sms.setAddress(cursor.getString(1));
                        sms.setDate(cursor.getLong(2));
                        sms.setType(cursor.getInt(3));
                        sms.setBody(cursor.getString(4));
                        smsList.add(sms);
                    }
                    cursor.close();
                    // 2.序列化到本地
                    writeToLocal(smsList);
                }
            }
        });
        Button read = findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream input = getApplicationContext()
                            .openFileInput("sms.txt");
                    byte[] temp = new byte[1024];
                    StringBuilder sb = new StringBuilder();
                    int len;
                    while ((len = input.read(temp)) > 0) {
                        sb.append(new String(temp, 0, len));
                    }
                    textView.setText(sb.toString());
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void writeToLocal(List<SmsInfo> smsList) {
        try {
            FileOutputStream output = getApplicationContext()
                    .openFileOutput("sms.txt", Context.MODE_PRIVATE);
            String s = "";
            for (SmsInfo smsInfo : smsList) {
                s += smsInfo;
            }
            output.write(s.getBytes());
            output.close();
            Toast.makeText(this, "备份成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "备份失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //内容观察者
    //自定义ContentObserver监听类
    private class SmsObserver extends ContentObserver {

        public SmsObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            //查询发件箱中的短信
            Cursor cursor = getContentResolver()
                    .query(Uri.parse("content://sms/outbox"), null, null, null, null);
            while (cursor.moveToNext()) {
                StringBuilder sb = new StringBuilder();
                sb.append("address=").append(cursor.getString(cursor.getColumnIndex("address")));
                sb.append(";subject=").append(cursor.getString(cursor.getColumnIndex("subject")));
                sb.append(";body=").append(cursor.getString(cursor.getColumnIndex("body")));
                sb.append(";time=").append(cursor.getLong(cursor.getColumnIndex("date")));
                System.out.println("发送短信：" + sb.toString());
            }
            cursor.close();
            super.onChange(selfChange);
        }
    }
}
