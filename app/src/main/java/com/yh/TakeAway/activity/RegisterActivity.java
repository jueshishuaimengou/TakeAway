package com.yh.TakeAway.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.yh.TakeAway.R;
import com.yh.TakeAway.dao.UserDao;
import com.yh.TakeAway.entity.User;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "mysql-party-register";
    private static final int PICK_IMAGE_REQUEST = 1; // 请求代码，用于相册选择
    private String profilePath; // 保存图片路径

    EditText username = null;
    EditText password = null;
    EditText phone = null;
    ImageButton profile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        // 绑定控件
        username = findViewById(R.id.userName);
        password = findViewById(R.id.userPassword);
        phone = findViewById(R.id.userPhone);
        profile = findViewById(R.id.imagebutton);

        // 设置点击事件打开相册
        profile.setOnClickListener(v -> openGallery());
    }

    // 打开相册
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // 处理选择图片的结果
    // 处理选择图片的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // 显示选中的图片
            profile.setImageURI(selectedImageUri);

            // 获取图片路径 (使用 ContentResolver)
            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImageUri);
                if (imageStream != null) {
                    profilePath = selectedImageUri.toString(); // 保存图片 URI
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "无法获取图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 注册用户
    // 注册用户
    public void register(View view) {
        String username1 = username.getText().toString();
        String userPassword1 = password.getText().toString();
        String userPhone1 = phone.getText().toString();

        if (username1.isEmpty() || userPassword1.isEmpty() || userPhone1.isEmpty()) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setUsername(username1);
        user.setPassword(userPassword1);
        user.setPhone(userPhone1);
        user.setRegistertime(new java.sql.Date(System.currentTimeMillis()));

        try {
            InputStream profileImageStream = null;
            if (profilePath != null) {
                Uri selectedImageUri = Uri.parse(profilePath);
                profileImageStream = getContentResolver().openInputStream(selectedImageUri);
            }

            InputStream finalProfileImageStream = profileImageStream;
            new Thread(() -> {
                UserDao userDao = new UserDao();
                boolean success = userDao.register(user, finalProfileImageStream);

                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_LONG).show();
                    }
                });
            }).start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "Selected image path: " + profilePath);

            Toast.makeText(getApplicationContext(), "无法加载图片", Toast.LENGTH_LONG).show();
        }
    }


    @SuppressLint("HandlerLeak")
    final Handler hand = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "该账号已经存在，请换一个账号", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2) {
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("a", "注册");
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        }
    };
}
