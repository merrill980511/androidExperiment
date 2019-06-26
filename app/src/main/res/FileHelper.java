package com.example.demo;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {
    private Context mContext;

    public FileHelper(Context mContext){
        super();
        this.mContext = mContext;
    }

    //保存文件的方法
    public void save(String fileName , String fileContent) throws Exception{
        //使用私有模式创建文件
        FileOutputStream output = mContext.openFileOutput(fileName , Context.MODE_PRIVATE);
        output.write(fileContent.getBytes());
        //关闭输出流
        output.close();
    }

    //读取文件的方法
    public String read(String fileName) throws IOException{
        //开启文件输入流
        FileInputStream input = mContext.openFileInput(fileName);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        //读取文件内容
        while ((len=input.read(temp))>0){
            sb.append(new String(temp , 0 ,len));
        }
        //关闭输入流
        input.close();

        return sb.toString();
    }
}
