package com.merrill.msg2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientThread extends Thread {
    private Socket s;
    // 定义向UI线程发送消息的Handler对象
    Handler handler;
    // 定义接收UI线程的Handler对象
    Handler revHandler;
    // 该线程处理Socket所对用的输入输出流
    BufferedReader br = null;
    OutputStream os = null;
    String ip;
    int port;

    public ClientThread(Handler handler, String ip, int port) {
        this.handler = handler;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        s = new Socket();
        //连接服务器 并设置连接超时为3秒
        try {
            s.connect(new InetSocketAddress(ip, port), 3000);
            Message msg = new Message();
            msg.what = 0x123;
            msg.obj = "connect successful!";
            handler.sendMessage(msg);
            Log.v("net", "connect successful!");
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            // 启动一条子线程来读取服务器相应的数据
            new Thread() {

                private boolean startPing(String ip) {
                    Log.e("Ping", "startPing...");
                    boolean success = false;
                    Process p = null;
                    ip = "192.168.124.8";
                    try {
                        p = Runtime.getRuntime().exec("ping -c 1 -i 0.2 -W 1 " + ip);
                        int status = p.waitFor();
                        if (status == 0) {
                            success = true;
                        } else {
                            success = false;
                        }
                    } catch (IOException e) {
                        success = false;
                    } catch (InterruptedException e) {
                        success = false;
                    } finally {
                        p.destroy();
                    }

                    return success;
                }

                @Override
                public void run() {
                    String content = null;
                    // 不断的读取Socket输入流的内容
                    try {
                        while ((content = br.readLine()) != null) {
                            // 每当读取到来自服务器的数据之后，发送的消息通知程序
                            // 界面显示该数据
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = content;
                            handler.sendMessage(msg);
                        }

                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }

            }.start();
            // 为当前线程初始化Looper
            Looper.prepare();
            // 创建revHandler对象
            revHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    // 接收到UI线程的中用户输入的数据
                    if (msg.what == 0x345) {
                        // 将用户在文本框输入的内容写入网络
                        try {
                            if (msg.obj == null){
                                msg.obj = "";
                            }
                            os.write((msg.obj.toString()).getBytes("gbk"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            };
            // 启动Looper
            Looper.loop();
        } catch (SocketTimeoutException e) {
            Message msg = new Message();
            msg.what = 0x123;
            msg.obj = "net connect time out!";
            Log.v("net", "net connect time out!");
            handler.sendMessage(msg);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void disconnect() {
        if (null != s) {
            try {
                System.out.println("close...");
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}