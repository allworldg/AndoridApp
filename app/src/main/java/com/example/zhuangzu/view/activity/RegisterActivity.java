package com.example.zhuangzu.view.activity;

import android.content.Context;
import android.os.Bundle;

import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.databinding.ActivityRegisterBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zhuangzu.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityRegisterBinding registerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());
        registerBinding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signUp:
                signUp();
                break;
            case R.id.right_slide_close:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    public void signUp() {
        final User user = new User();
        String userName = registerBinding.etRegistUserName.getText().toString();
        String nickName = registerBinding.etRegistNickName.getText().toString();
        String password = registerBinding.etLoginPassword.getText().toString();
        user.setUsername(userName);
        user.setNickName(nickName);
        user.setPassword(password);
        if (userName.equals("") || nickName.equals("") || password.equals("")) {
            Util.myLog(RegisterActivity.this, "账号，用户名和密码不得为空");
            return;
        }

        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    BmobUser.logOut();//bmob注册函数注册成功后会自动登陆，所以先logout。
                    RegisterActivity.this.onBackPressed();
                } else {
                    Log.d("TAG", e.getMessage());
                    switch (e.getMessage()) {
                        case "username 'test' already taken.":
                            Util.myLog(RegisterActivity.this, "账号已存在，请重新输入");
                            break;
                        default:
                            break;
                    }
                }
            }

        });
    }
}