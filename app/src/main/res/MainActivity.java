package com.example.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.R;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText editName;
    private EditText editDetail;
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
        editDetail = (EditText)findViewById(R.id.editDetail);
        editName = (EditText)findViewById(R.id.editName);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnClean = (Button)findViewById(R.id.btnClean);
        btnRead = (Button)findViewById(R.id.btnRead);

        btnClean.setOnClickListener((View.OnClickListener) this);
        btnRead.setOnClickListener((View.OnClickListener) this);
        btnSave.setOnClickListener((View.OnClickListener)this);

    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnClean:
                editName.setText("");
                editDetail.setText("");
                break;
            case R.id.btnSave:
                FileHelper fHelper = new FileHelper(mContext);
                String fileName = editName.getText().toString();
                String fileDetail = editDetail.getText().toString();

                try{
                    fHelper.save(fileName,fileDetail);
                    Toast.makeText(getApplicationContext() , "数据写入成功",Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext() , "数据写入失败" , Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.btnRead:
                String detail = "";
                FileHelper fHelper2 = new FileHelper(getApplicationContext());
                try{
                    String fname = editName.getText().toString();
                    detail = fHelper2.read(fname);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext() , detail , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
