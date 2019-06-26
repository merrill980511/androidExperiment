package com.merrill.database;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<Bean> list;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase db;
    private ListView lv;
    private int chosen = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = this.findViewById(R.id.lv);
        myOpenHelper = new MyOpenHelper(this);
        db = myOpenHelper.getWritableDatabase();
        list = new ArrayList<Bean>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                if (chosen == position) {
                    // Cancel selection
                    lv.setSelector(R.drawable.unhighlight);
                    chosen = -1;
                } else {
                    chosen = position;
                    Drawable drawable = getResources().getDrawable(R.drawable.highlight);
                    lv.setSelector(drawable);
                }
            }
        });
        refresh(null);
    }

    public void add(View view) {
        // 该方法只用于跳转，实际增加步骤在增加页面
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);

    }

    public void refresh(View view) {
        list.clear();
        Cursor cursor = db.rawQuery("select * from bills;", null);
        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String t_time = cursor.getString(cursor.getColumnIndex("t_time"));
            String t_action = cursor.getString(cursor.getColumnIndex("t_action"));
            String amount = cursor.getString(cursor.getColumnIndex("t_amount"));
            Bean bean = new Bean();
            bean.setId(id);
            bean.setT_time(t_time);
            bean.setT_action(t_action);
            bean.setT_amount(amount);
            list.add(bean);
        }
        MyAdapter myAdapter = new MyAdapter(this, list, R.layout.item);
        lv.setAdapter(myAdapter);
        cursor.close();
    }

    public void delete(View view) {
        if (chosen != -1) {
            int id = list.get(chosen).getId();
            db = myOpenHelper.getWritableDatabase();
            db.delete("bills", "id = ?", new String[]{String.valueOf(id)});
            refresh(null);
        }
    }

}