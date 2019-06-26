package com.merrill.msg2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    @Override
    public void run() {
        Socket socket;
        try {
            ServerSocket server = new ServerSocket(10101);
            //循环监听客户端链接请求
            while (true) {
                System.out.println("start...");
                //接收请求
                socket = server.accept();
                System.out.println("accept...");
                //接收客户端消息
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = in.readLine();
                System.out.println("Server:" + message);
                //关闭流
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //启动服务器
    public static void main(String[] args) {
        Thread server = new Thread(new Server());
        server.start();
    }
}