package com.merrill.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {

    private EditText et_time;
    private EditText et_action;
    private EditText et_amount;
    private SQLiteDatabase db;
    private Bean bean;
    private long results;
    private MyOpenHelper myOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 加载布局
        setContentView(R.layout.activity_add);
        et_time = findViewById(R.id.et_time);
        et_action = findViewById(R.id.et_action);
        et_amount = findViewById(R.id.et_amount);
        myOpenHelper = new MyOpenHelper(getApplicationContext());
    }

    public void click_add(View view) {
        db = myOpenHelper.getWritableDatabase();
        String time = et_time.getText().toString().trim();
        String action = et_action.getText().toString().trim();
        String amount = et_amount.getText().toString().trim();
        if (TextUtils.isEmpty(time) || TextUtils.isEmpty(action) || TextUtils.isEmpty(amount)) {
            Toast.makeText(getApplicationContext(), "输入内容不能为空", Toast.LENGTH_LONG).show();
            return;
        } else {

            bean = new Bean();
            bean.setT_time(time);
            bean.setT_action(action);
            bean.setT_amount(amount);
            ContentValues values = new ContentValues();
            values.put("t_time", time);
            values.put("t_action", action);
            values.put("t_amount", amount);
            results = db.insert("bills", "id", values);
            db.close();
            System.out.println(results);
            if (results > 0) {
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                AddActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "系统繁忙,请稍后再试！", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void click_return(View view) {
        AddActivity.this.finish();
    }
}
