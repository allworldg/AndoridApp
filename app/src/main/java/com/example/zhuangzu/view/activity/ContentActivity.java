package com.example.zhuangzu.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.bean.Article;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.databinding.ActivityContentBinding;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityContentBinding binding;
    private String detailData;
    private String contentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContentBinding.inflate(getLayoutInflater());
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(binding.getRoot());
        detailData = getIntent().getStringExtra("url");
        contentId = getIntent().getStringExtra("contentId");
        binding.wbview.setWebViewClient(new WebViewClient());
        WebSettings webSettings = binding.wbview.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        binding.wbview.loadUrl(detailData);
        binding.share.setOnClickListener(this);
        binding.favorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,detailData);
                startActivity(intent);
                break;
            case R.id.favorite:
                if(!BmobUser.isLogin()){
                    Util.myToast(ContentActivity.this,"请先登陆");
                }else{
                    addCollect(contentId);
                }
        }
    }

    public static void actionStart(Context context, String Url,String contentId){
        Intent intent = new Intent(context,ContentActivity.class);
        intent.putExtra("url",Url);
        intent.putExtra("contentId",contentId);
        context.startActivity(intent);
    }
    //双向添加关系
    public void addCollect(String contentId){
//        User user = BmobUser.getCurrentUser(User.class);//先在article里面添加指向user的关系
        User user = new User();
        user.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
        Article article = new Article();
        article.setObjectId(contentId);
        BmobRelation relation = new BmobRelation(); //暂时不用，打算用来显示文章被喜欢的数量
        relation.add(user);
        article.setLike(relation);
        article.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Util.myToast(ContentActivity.this,"收藏成功");
                }else{
                    Log.d("error","更新失败"+e.getMessage());
                }
            }
        });
        BmobRelation relation1 = new BmobRelation();
        relation1.add(article);
        user.setMlike(relation1);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e!=null){
                    Log.d("error","更新失败"+e.getMessage());
                }
            }
        });

    }
}