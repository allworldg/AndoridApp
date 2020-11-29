package com.example.zhuangzu.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.databinding.ActivityRegisterBinding;
import com.example.zhuangzu.databinding.ActivityUserInformationBinding;
import com.example.zhuangzu.view.dialog.ChangeNameDialog;

import java.io.IOException;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UserInformationActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityUserInformationBinding informationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        informationBinding = ActivityUserInformationBinding.inflate(getLayoutInflater());
        setContentView(informationBinding.getRoot());
        informationBinding.rightSlideClose.setOnClickListener(this);
        informationBinding.rightArrowChangeUserIv.setOnClickListener(click_new);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_slide_close:
                onBackPressed();
                break;
            default:
                break;

        }
    }
    View.OnClickListener click_new = v -> {
        ChangeNameDialog.OnSureClickListener listener1 = new ChangeNameDialog.OnSureClickListener() {
            public void getText(String string) {
                informationBinding.tvName.setText(string);
                User user = BmobUser.getCurrentUser(User.class);
                user.setNickName(string);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Util.myLog(UserInformationActivity.this,"修改成功");
                        }
                    }
                });
            }
        };
        ChangeNameDialog d1 = new ChangeNameDialog(UserInformationActivity.this, listener1);
        d1.show();
    };
}