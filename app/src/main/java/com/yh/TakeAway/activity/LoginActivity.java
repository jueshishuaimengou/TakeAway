package com.yh.TakeAway.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yh.TakeAway.R;
import com.yh.TakeAway.dao.UserDao;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "mysql-party-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void reg(View view){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }


    /**
     * function: 登录
     * */
    public void login(View view){

        EditText EditTextPhone = findViewById(R.id.log_uesrPhone);
        EditText EditTextPassword = findViewById(R.id.log_Password);

        new Thread(){
            @Override
            public void run() {
                UserDao userDao = new UserDao();
                int msg = userDao.login(EditTextPhone.getText().toString(),EditTextPassword.getText().toString());
                hand1.sendEmptyMessage(msg);
            }
        }.start();

    }

    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                // 跳转到 MainActivity
                EditText accountText=findViewById(R.id.log_uesrPhone);
                EditText pwdText=findViewById(R.id.log_Password);

                SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                String account=accountText.getText().toString();
                String pwd=pwdText.getText().toString();
                edit.putString("account",account);
                edit.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                // 可选：关闭当前的 LoginActivity
                finish();
            } else if (msg.what == 2){
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3){
                Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
            }
        }
    };

}
