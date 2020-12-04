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
import android.widget.HeaderViewListAdapter;

import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.adapter.ArticleAdapter;
import com.example.zhuangzu.adapter.SortAdapter;
import com.example.zhuangzu.bean.Article;
import com.example.zhuangzu.databinding.ActivitySortBinding;
import com.example.zhuangzu.view.fragment.LeftMenuFragment;
import com.example.zhuangzu.view.fragment.RightMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.internal.operators.observable.ObservableRefCount;

public class SortActivity extends AppCompatActivity {
    ActivitySortBinding binding;
    private String type;//文章分类
    private SortAdapter sortAdapter;
    private ArrayList<Article> articles;
    private RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySortBinding.inflate(getLayoutInflater());
        type = getIntent().getStringExtra("type");
        setContentView(binding.getRoot());
        binding.swipeSort.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                       updateArticles(type);
                        binding.swipeSort.setRefreshing(false);
                    }
                }
        );

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initMenu();
        initArticles(type);


    }

    private void initMenu() {
        recyclerView = binding.sortRecycle;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        articles = new ArrayList<>();
        sortAdapter = new SortAdapter(SortActivity.this,articles);
        sortAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ContentActivity.actionStart(SortActivity.this,articles.get(position).getUrl());
            }
        });
        recyclerView.setAdapter(sortAdapter);


    }
    public void initArticles(String type){
        BmobQuery<Article> query = new BmobQuery<>();
        query.addWhereEqualTo("type",type);
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
                if(e==null){
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 0;
                    msg.obj = list;
                    ArticleListHandler.sendMessage(msg);
                }else {
                    Log.d("error",e.getMessage());
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 2;
                    ArticleListHandler.sendMessage(msg);
                }
            }
        });
    }

    public void updateArticles(String type){
        BmobQuery<Article> query = new BmobQuery<>();
        query.addWhereEqualTo("type",type);
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
                if(e==null){
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 1;
                    msg.obj = list;
                    ArticleListHandler.sendMessage(msg);
                }else {
                    Log.d("error",e.getMessage());
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
            switch (msg.what){
                case 0:
                    articles = (ArrayList<Article>)msg.obj;
                    sortAdapter.setArticles(articles);
                    Log.d("update","update");
                    sortAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    articles = (ArrayList<Article>)msg.obj;
                    sortAdapter.setArticles(articles);
                    Log.d("update","update");
                    sortAdapter.notifyDataSetChanged();
                    Util.myToast(SortActivity.this,"刷新成功");
                    break;
                case 2:
                    Util.myToast(SortActivity.this,"由于网络原因，刷新失败");
                default:
                    break;
            }

        }
    };
    public static void actionStart(Context context, String type) {
        Intent intent = new Intent(context, SortActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }
}