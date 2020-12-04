package com.example.zhuangzu.view.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.FileUtils;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.databinding.ActivityUserInformationBinding;
import com.example.zhuangzu.view.dialog.ChangeNameDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserInformationActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityUserInformationBinding informationBinding;
    User user;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        informationBinding = ActivityUserInformationBinding.inflate(getLayoutInflater());
        setContentView(informationBinding.getRoot());
        user = BmobUser.getCurrentUser(User.class);
        if(user.getHeadPicture()!=null){
            show(user.getHeadPicture().getFileUrl());
        }
        informationBinding.rightSlideClose.setOnClickListener(this);
        informationBinding.rightArrowChangePassword.setOnClickListener(this);
        informationBinding.rightArrowChangeUserIv.setOnClickListener(click_new);
        informationBinding.headPicIv.setOnClickListener(this);
        informationBinding.tvName.setText(user.getNickName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_slide_close:
                onBackPressed();
                break;
            case R.id.rightArrow_changePassword:
                toPasswordAcitivy();
                break;
            case R.id.headPic_iv:
                uploadPhoto();
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
                            Util.myToast(UserInformationActivity.this,"修改成功");
                        }
                    }
                });
            }
        };
        ChangeNameDialog d1 = new ChangeNameDialog(UserInformationActivity.this, listener1);
        d1.show();
    };//修改用户名的点击事件

    public void toPasswordAcitivy(){
        Intent intent = new Intent(UserInformationActivity.this,PasswordActivity.class);
        startActivity(intent);
    }

    public void uploadPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri uri =data.getData();
                upImageToServer(uri);
                Glide.with(this).load(uri).into(informationBinding.headPicIv);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void upImageToServer(Uri uri){
        String path = FileUtils.getPath(getApplicationContext(),uri);
        Log.d("path",path);
        BmobFile userIcon = new BmobFile(new File(path));
        userIcon.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e!=null){
                    Log.d("upException",e.getMessage());
                }else{
                    Log.d("upsucess",userIcon.getFileUrl());
                    user.setHeadPicture(userIcon);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e!=null){
                                Util.myToast(UserInformationActivity.this,"头像更新成功");
                                Log.d("success",user.getHeadPicture().toString());
                            }
                            else{
                                Log.d("errorupload",e.getMessage());
                            }
                        }
                    });
                }
            }
        });

    }

    public void show(String uri){
        if(uri.startsWith("http://")){
            uri = uri.replace("http://","https://");//glide不支持http了，所以要替换
        }
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.avater_default);
        Glide.with(this).load(uri).into(informationBinding.headPicIv);
    }
}