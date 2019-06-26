package com.merrill.msg2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    EditText host, input;
    TextView show;
    Button send, connect;
    Handler handler;

    ClientThread clientThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        host = findViewById(R.id.host);
        input = findViewById(R.id.input);
        show = findViewById(R.id.show);
        send = findViewById(R.id.send);
        connect = findViewById(R.id.connect);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    // 将读取的内容追加显示在文本框中
                    show.append("\n" + msg.obj.toString());
                }
            }
        };

        connect.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    boolean flag = true;
    @Override
    public void onClick(View v) {
        if (v.equals(connect)) {
            if (flag) {
                String strip = host.getText().toString().trim();
                if (strip.indexOf(":") > 0) {
                    String s[] = strip.split(":");
                    int port = Integer.parseInt(s[1]);
                    clientThread = new ClientThread(handler, s[0], port);
                    new Thread(clientThread).start();
                    connect.setText(R.string.disconnect);
                } else {
                    Toast.makeText(MainActivity.this, "输入格式有误", Toast.LENGTH_SHORT).show();
                }
            } else {
                connect.setText(R.string.connect);
                clientThread.disconnect();
                show.append("\n" + "disconnect successful!");
                host.setText("");
            }
            flag = !flag;
        } else if (v.equals(send)){
            try {
                if (!input.getText().toString().trim().equals("")) {
                    // 当用户按下按钮之后，将用户输入的数据封装成Message
                    // 然后发送给子线程Handler
                    Message msg = new Message();
                    msg.what = 0x345;
                    msg.obj = input.getText().toString() + "\n";
                    clientThread.revHandler.sendMessage(msg);
                    input.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "输入不可为空", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}