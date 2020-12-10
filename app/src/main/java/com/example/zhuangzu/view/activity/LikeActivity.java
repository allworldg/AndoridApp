package com.example.zhuangzu.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.adapter.likeAdapter;
import com.example.zhuangzu.bean.Article;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.databinding.ActivityLikeBinding;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LikeActivity extends AppCompatActivity {
    ActivityLikeBinding binding;
    private likeAdapter likeAdapter;
    private ArrayList<Article> articles;
    private RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLikeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initMenu();
        initArticles();
        binding.swipeSort.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateArticles();
                        binding.swipeSort.setRefreshing(false);
                    }
                }
        );


    }
    public void initMenu(){
        articles = new ArrayList<>();
        recyclerView = binding.likeRecycle;
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        likeAdapter = new likeAdapter(LikeActivity.this,articles);
        likeAdapter.setOnItemClickListener(new likeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ContentActivity.actionStart(LikeActivity.this,articles.get(position).getUrl()
                ,articles.get(position).getObjectId());
            }
        });
        recyclerView.setAdapter(likeAdapter);

    }
    //初始化数据
    public void initArticles(){
        BmobQuery<Article> query = new BmobQuery<>();
        User user = BmobUser.getCurrentUser(User.class);
        query.addWhereRelatedTo("mlike",new BmobPointer(user));
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
                if(e==null){
                        Message msg = ArticleListHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = list;
                        ArticleListHandler.sendMessage(msg);
                }else{
                    Log.d("error", e.getMessage());
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 2;
                    ArticleListHandler.sendMessage(msg);
                }
            }
        });
    }
    //刷新更新数据
    public void updateArticles(){
        BmobQuery<Article> query = new BmobQuery<>();
        User user = BmobUser.getCurrentUser(User.class);
        query.addWhereRelatedTo("mlike",new BmobPointer(user));
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
                if(e==null){
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 1;
                    msg.obj = list;
                    ArticleListHandler.sendMessage(msg);
                }else{
                    Log.d("error", e.getMessage());
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 2;
                    ArticleListHandler.sendMessage(msg);
                }
            }
        });
    }

    private Handler ArticleListHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://初始化
                    articles = (ArrayList<Article>) msg.obj;
                    likeAdapter.setArticles(articles);
                    likeAdapter.notifyDataSetChanged();
                    break;
                case 1://刷新
                    articles = (ArrayList<Article>) msg.obj;
                    likeAdapter.setArticles(articles);
                    likeAdapter.notifyDataSetChanged();
                    Util.myToast(LikeActivity.this, "刷新成功");
                    break;
                case 2://刷新失败
                    Util.myToast(LikeActivity.this, "由于网络原因，刷新失败");
                default:
                    break;
            }

        }
    };

    public static void actionStart(Context context){
        Intent intent = new Intent(context,LikeActivity.class);
        context.startActivity(intent);
    }
}