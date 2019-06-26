package com.merrill.saveFile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText editName;
    private EditText editContent;
    private Button btnSave;
    private Button btnClean;
    private Button btnRead;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        bindViews();
    }

    private void bindViews() {
        editContent = (EditText) findViewById(R.id.editContent);
        editName = (EditText) findViewById(R.id.editName);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnClean = (Button) findViewById(R.id.btnClean);
        btnRead = (Button) findViewById(R.id.btnRead);

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName.setText("");
                editContent.setText("");
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtil fileUtil = new FileUtil(getApplicationContext());
                try {
                    String AbsolutePath = getFilesDir().getAbsolutePath();
                    String fileName = editName.getText().toString();
                    if (!fileUtil.isExist(AbsolutePath + "/" + fileName)) {
                        Toast.makeText(getApplicationContext(), "文件不存在", Toast.LENGTH_SHORT).show();
                    } else {
                        String content = fileUtil.read(fileName);
                        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "数据读取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtil fileUtil = new FileUtil(mContext);
                String fileName = editName.getText().toString();
                String fileContent = editContent.getText().toString();
                if ("".equals(fileName)){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("warning")
                            .setMessage("输入文件名不能为空")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                    return;
                }
                try {
                    String AbsolutePath = getFilesDir().getAbsolutePath();
                    if (fileUtil.isExist(AbsolutePath + "/" + fileName)) {
                        new AlertDialog.Builder(MainActivity.this).setTitle("警告").setMessage(R.string.confirm)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                       .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                return;
                            }
                        })
                       .show();
                    }
                        fileUtil.save(fileName, fileContent);
                        Toast.makeText(getApplicationContext(), "数据写入成功", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "数据写入失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
