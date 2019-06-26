package com.merrill.database;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Bean> list = new ArrayList<Bean>();
    private LayoutInflater inflator;
    private int resore;
    private TextView t_time;
    private TextView t_action;
    private TextView t_amount;

    public MyAdapter(Context context, ArrayList<Bean> list, int resore) {

        this.context = context;
        this.list = list;
        this.resore = resore;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflator = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(resore, null);
            t_time = convertView.findViewById(R.id.tv_time);
            t_time.setGravity(Gravity.CENTER);
            t_action = convertView.findViewById(R.id.tv_action);
            t_action.setGravity(Gravity.CENTER);
            t_amount = convertView.findViewById(R.id.tv_amount);
            t_amount.setGravity(Gravity.CENTER);
        }
        Bean bean = list.get(position);
        t_time.setText(bean.getT_time());
        t_action.setText(bean.getT_action());
        t_amount.setText(bean.getT_amount());
        return convertView;
    }

}