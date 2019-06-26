package com.merrill.myapplication4;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.myapplication4.R;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button bn = null;
    Button button=null;
    Button button1=null;
    Button button2=null;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermisionUtils.verifyStoragePermissions(this);
        db = SQLiteDatabase.openOrCreateDatabase("/storage/emulated/0/Music/my.db3", null);
        listView = findViewById(R.id.show);
        bn = findViewById(R.id.ok);
        button=findViewById(R.id.ck);
        button1=findViewById(R.id.xg);
        button2=findViewById(R.id.sc);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ContentValues values=new ContentValues();
                values.put("news_content","eat");
                int result=db.update("news_inf",values,"news_title=?",new String[]{"6/1"});

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int result=db.delete("news_inf","news_title=?",new String[]{"6/1"});

            }
        });






        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                db.close();
                Intent intent = new Intent(MainActivity.this, tianjia.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View ve)
            {
               Cursor cursor = db.rawQuery("select * from news_inf", null);
                inflateList(cursor);
            }
        });
    }
    public void inflateList(Cursor cursor) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.line, cursor,
                new String[]{ "news_title", "news_content","news_mo"},
                new int[]{R.id.my_title, R.id.my_content,R.id.mo},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
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
