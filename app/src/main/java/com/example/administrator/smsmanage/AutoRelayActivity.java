package com.example.administrator.smsmanage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AutoRelayActivity extends Activity implements View.OnClickListener{

    private EditText numberET;

    private Button btn_DeleteNumber;

    private Button btn_AddNumber;

    private EditText stringET;

    private Button btn_DeleteString;

    private Button btn_AddString;

    private SharedPreferences preferences;

    private TextView number_tv;

    private TextView string_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_relay);
        findView();
        initData();

    }

    private void findView(){
        numberET=(EditText)findViewById(R.id.editTextNumber);
        btn_DeleteNumber=(Button)findViewById(R.id.buttonDelete1);
        btn_AddNumber=(Button)findViewById(R.id.buttonAddNumber);
        stringET=(EditText)findViewById(R.id.editTextString);
        btn_DeleteString=(Button)findViewById(R.id.buttonDelete2);
        btn_AddString=(Button)findViewById(R.id.buttonAddString);
        number_tv=(TextView)findViewById(R.id.textViewNumber);
        string_tv=(TextView)findViewById(R.id.textViewString);
        btn_DeleteNumber.setOnClickListener(this);
        btn_AddNumber.setOnClickListener(this);
        btn_AddString.setOnClickListener(this);
        btn_DeleteString.setOnClickListener(this);

    }

    private void initData(){
        preferences=getSharedPreferences("smsdata",MODE_PRIVATE);
        String phoneNumer=preferences.getString("phoneNumber","");
        if (phoneNumer.equals("")){
            numberET.setHint("请输入号码");
        }else  {
          //  numberET.setText(phoneNumer);
            number_tv.setText(phoneNumer);
        }
        String content=preferences.getString("replyContent","");
        if (content.equals("")){
            stringET.setHint("请输入内容");
        }else {
         //   stringET.setText(content);
            string_tv.setText(content);
        }
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor=preferences.edit();
        switch (view.getId()){
            case R.id.buttonDelete1:
                editor.putString("phoneNumber", "");
                Toast.makeText(this,"删除成功",Toast.LENGTH_LONG).show();
                editor.apply();
                number_tv.setText("没有号码");
                break;

            case R.id.buttonAddNumber:
                String str=numberET.getText().toString();
                editor.putString("phoneNumber",str);
                editor.apply();
                Toast.makeText(this,"添加成功",Toast.LENGTH_LONG).show();
                number_tv.setText(str);
                numberET.setText("");
                break;

            case R.id.buttonDelete2:
//                File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs","smsdata.xml");
//                if (file.exists()){
//                    file.delete();
//                }
                editor.putString("replyContent", "");
                Toast.makeText(this,"删除成功",Toast.LENGTH_LONG).show();
                editor.apply();
                string_tv.setText("没有内容");
                break;

            case R.id.buttonAddString:
                editor.putString("replyContent", stringET.getText().toString());
                editor.apply();
                Toast.makeText(this,"添加成功",Toast.LENGTH_LONG).show();
                string_tv.setText(stringET.getText().toString());
                stringET.setText("");
                break;
            default:
                editor.apply();
        }


    }



}
