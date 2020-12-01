package com.example.zhuangzu.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.zhuangzu.databinding.ActivitySettingBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.zhuangzu.R;

import cn.bmob.v3.BmobUser;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySettingBinding settingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(settingBinding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        settingBinding.btnLogout.setOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
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