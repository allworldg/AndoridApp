package com.example.zhuangzu.view.activity;

import android.os.Bundle;

import com.example.zhuangzu.databinding.ActivitySettingBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.zhuangzu.R;

import cn.bmob.v3.BmobUser;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySettingBinding settingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(settingBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        settingBinding.btnLogout.setOnClickListener(this);
        if (actionBar != null) {
            actionBar.hide();
        }
        if(!BmobUser.isLogin()){
            settingBinding.btnLogout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                BmobUser.logOut();
                onBackPressed();
        }
    }
}