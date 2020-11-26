package com.example.zhuangzu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.zhuangzu.databinding.ActivitySplashBinding;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import cn.bmob.v3.Bmob;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding spBinding;
    private static long mLastClickTime;
    private static int REQUEST_PERMISSION_CODE = 1;
    /**
     * 需要进行检测的权限数组
     */
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(spBinding.getRoot());
        Bmob.initialize(this, "6e33e1bd293e7052ed10a6f000b011cc");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
        ObjectfeiruAnimator();//加载开始图片
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }//申请权限

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTime <= 2000L) {
            super.onBackPressed();
        } else {
            mLastClickTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }

    }


}


