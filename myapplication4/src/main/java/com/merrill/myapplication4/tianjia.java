package com.merrill.myapplication4;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication4.R;

public class tianjia extends AppCompatActivity
{
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        PermisionUtils.verifyStoragePermissions(this);
        db = SQLiteDatabase.openOrCreateDatabase("/storage/emulated/0/Music/my.db3", null);
        Button bn = (Button) findViewById(R.id.ok2);
        bn.setOnClickListener(new View.OnClickListener()
        {
                                  @Override
                                  public void onClick(View v)
                                  {
                                      String title = ((EditText) findViewById(R.id.title2)).getText().toString();
                                      String content = ((EditText) findViewById(R.id.content2)).getText().toString();
                                      String money = ((EditText) findViewById(R.id.content3)).getText().toString();
                                      try
                                      {

                                         insertData(db, title, content, money);

                                      } catch (SQLiteAbortException e)
                                      {
                                          db.execSQL("create table news_inf(_id integer primary "
                                                  + " key autoincrement,"
                                                  + " news_title varchar(50),"
                                                  + " news_content varchar(50),"
                                                  + " news_mo varchar(50))");
                                          insertData(db, title, content, money);
                                      }
                                      db.close();
                                      Intent intent = new Intent(tianjia.this, MainActivity.class);
                                      startActivity(intent);
                                      finish();
                                  }
        });
    }
    public void insertData(SQLiteDatabase db, String title, String content,String money)
    {
        db.execSQL("insert into news_inf values(null,?,?,?)"
                , new String[]{title, content, money});
    }

    public void onDestroy()
    {
        super.onDestroy();
        if(db!=null&&db.isOpen())
        {
            db.close();
        }


    }
}
