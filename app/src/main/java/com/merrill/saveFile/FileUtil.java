package com.merrill.saveFile;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    private Context mContext;

    public FileUtil(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isExist(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void save(String fileName, String fileContent) throws Exception {
        FileOutputStream output = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
        output.write(fileContent.getBytes());
        output.close();
    }

    public String read(String fileName) throws IOException {
        FileInputStream input = mContext.openFileInput(fileName);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len;
        while ((len = input.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        input.close();
        return sb.toString();
    }
}
