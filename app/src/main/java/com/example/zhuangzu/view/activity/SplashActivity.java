package com.example.zhuangzu.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.zhuangzu.databinding.ActivitySplashBinding;

import cn.bmob.v3.Bmob;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding spBinding;
    private static long mLastClickTime;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(spBinding.getRoot());
        requestMyPermissions();//申请读写权限
        Bmob.initialize(this, "3fdb919b080c6aec487233c1f30126ab");//bmob初始化
        ActionBar actionBar = getSupportActionBar();//隐藏系统导航栏
        if (actionBar != null) {
            actionBar.hide();
        }
        ObjectfeiruAnimator();//加载开始图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//系统状态栏透明
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void ObjectfeiruAnimator() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(spBinding.loadPicture, "translationX", -100F);
//        ObjectAnimator animatorY = ObjectAnimator.ofFloat(spBinding.loadPicture,"translationY",200,0);
        ObjectAnimator animatorF = ObjectAnimator.ofFloat(spBinding.loadPicture, "alpha", 0.0F, 1.0F);
        animatorX.setDuration(1500L);

        animatorF.setDuration(3000);
        AnimatorSet animatorSet = new AnimatorSet();//创建动画集
        animatorSet.playTogether(animatorF, animatorX);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }//加载图


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTime <= 2000L) {
            super.onBackPressed();
        } else {
            mLastClickTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }

    }

    private void requestMyPermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //没有授权，编写申请权限ggh代码
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else {
            Log.d("TAG", "requestMyPermissions: 有写SD权限");
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //没有授权，编写申请权限代码
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        } else {
            Log.d("TAG", "requestMyPermissions: 有读SD权限");
        }
    }

}


