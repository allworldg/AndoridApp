package com.example.zhuangzu.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.zhuangzu.R;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.databinding.ActivityLoginBinding;
import com.example.zhuangzu.databinding.ActivityMainBinding;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        activityLoginBinding.btnLogin.setOnClickListener(this);
        activityLoginBinding.rightSlideClose.setOnClickListener(this);
        activityLoginBinding.signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_slide_close:
                onBackPressed();
                break;
            case R.id.btn_login:
                login(activityLoginBinding.etLoginName.getText().toString(), activityLoginBinding.etLoginPassword.getText().toString());
                break;
            case R.id.signUp:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    public void login(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    switch (e.getMessage()) {
                        case "username or password incorrect.":
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(LoginActivity.this, "账号或密码不得为空", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }
        });
    }
}