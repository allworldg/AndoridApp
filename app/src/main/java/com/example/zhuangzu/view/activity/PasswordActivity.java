package com.example.zhuangzu.view.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.databinding.ActivityPasswordBinding;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityPasswordBinding passwordBinding;
    BmobUser user;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        passwordBinding = ActivityPasswordBinding.inflate(getLayoutInflater());
        setContentView(passwordBinding.getRoot());
        passwordBinding.rightSlideClose.setOnClickListener(this);
        passwordBinding.btnUpdate.setOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                updatePassword(passwordBinding.etOriginName.getText().toString(), passwordBinding.etNewPassword.getText().toString());
                break;
            case R.id.right_slide_close:
                onBackPressed();
            default:
                break;
        }
    }

    public void updatePassword(String oldStr, String newStr) {
        user = BmobUser.getCurrentUser(User.class);
        user.setPassword(newStr);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Util.myToast(PasswordActivity.this, "更新密码成功");
                    onBackPressed();


                } else {
                    Util.myToast(PasswordActivity.this, "请输入正确的密码");
                    return;
                }
            }
        });
    }
}