package com.example.administrator.smsmanage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForWardActivity extends Activity implements View.OnClickListener{

    private EditText forwardET;

    private Button btn_ForwardDetele;

    private Button btn_ForwardAdd;

    private SharedPreferences preferences;

    private TextView forward_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_ward);
        findView();
        initData();
    }

    private  void  findView(){
        forwardET=(EditText)findViewById(R.id.editTextForward);
        btn_ForwardAdd=(Button)findViewById(R.id.buttonAddForward);
        btn_ForwardDetele=(Button)findViewById(R.id.buttonDelete3);
        forward_tv=(TextView)findViewById(R.id.textViewForward);
        btn_ForwardAdd.setOnClickListener(this);
        btn_ForwardDetele.setOnClickListener(this);
    }

    private  void initData(){
        preferences=getSharedPreferences("smsdata",MODE_PRIVATE);
        String number=preferences.getString("forwardNumber","");
        if (number.equals("")){
            forwardET.setHint("请输入号码");
        }else {
//            forwardET.setText(number);
            forward_tv.setText(number);
        }

    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor=preferences.edit();
        switch (view.getId()){
            case R.id.buttonAddForward:
                editor.putString("forwardNumber", forwardET.getText().toString());
                editor.apply();
                Toast.makeText(this,"添加成功",Toast.LENGTH_LONG).show();
                forward_tv.setText(forwardET.getText().toString());
                forwardET.setText("");
                break;

            case R.id.buttonDelete3:
                editor.putString("forwardNumber","");
                editor.apply();
                Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
                forward_tv.setText("没有号码");
                break;

        }
    }
}
