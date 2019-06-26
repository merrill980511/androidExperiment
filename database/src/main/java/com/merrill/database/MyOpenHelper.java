package com.merrill.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    // context:上下文
    // name:数据库名字
    // 目的创建cursor对象
    // 数据库的版本从一开始
    public MyOpenHelper(Context context) {
        super(context, "expdb", null, 1);
    }

    @Override
    // 当数据库第一次被创建的时候调用，用来建表
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table bills (id integer PRIMARY KEY autoincrement, t_time varchar(20),t_action varchar(20),t_amount varchar(20));");
        db.execSQL("insert into bills (t_time, t_action, t_amount) values('2019.5.21','吃饭','20.5');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}