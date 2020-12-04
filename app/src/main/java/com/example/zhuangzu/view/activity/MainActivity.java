package com.example.zhuangzu.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.adapter.ArticleAdapter;
import com.example.zhuangzu.bean.Article;
import com.example.zhuangzu.databinding.ActivityMainBinding;
import com.example.zhuangzu.view.fragment.LeftMenuFragment;
import com.example.zhuangzu.view.fragment.RightMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mainBinding;
    private LeftMenuFragment leftMenu;
    private RightMenuFragment rightMenu;
    private SlidingMenu slidingMenu;
    private ArrayList<Article> articles;
    private long mLastClickTime;
    ArticleAdapter articleAdapter ;
    private RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        articles = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mainBinding.swipeMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateArticles();
                mainBinding.swipeMain.setRefreshing(false);
            }
        });
        initArticles();//初始化数据
        initMenu();//初始化菜单栏
    }

    private void initMenu() {
        recyclerView = mainBinding.mainRecycleView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        articleAdapter = new ArticleAdapter(MainActivity.this,articles);
        articleAdapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ContentActivity.actionStart(MainActivity.this,articles.get(position).getUrl()
                        ,articles.get(position).getObjectId());
            }
        });
        recyclerView.setAdapter(articleAdapter);
        //设置左右布局
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        // 设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setFadeEnabled(true);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.left_menu);//设置左侧布局
        leftMenu = new LeftMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.left_menu, leftMenu).commit();
        slidingMenu.setSecondaryMenu(R.layout.right_menu);//设置右侧布局
        rightMenu = new RightMenuFragment();//
        getSupportFragmentManager().beginTransaction().add(R.id.right_menu, rightMenu).commit();
        mainBinding.leftSlide.setOnClickListener(this);
        mainBinding.rightSlide.setOnClickListener(this);
    }

    public void initArticles() {
        BmobQuery<Article> query = new BmobQuery<>();
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
                if (e == null) {
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 0;
                    msg.obj = list;
                    ArticleListHandler.sendMessage(msg);
                } else {
                    Log.d("ArticleException", e.getMessage());
                }
            }
        });
    }
    public void updateArticles() {
        BmobQuery<Article> query = new BmobQuery<>();
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
                if (e == null) {
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 1;
                    msg.obj = list;
                    ArticleListHandler.sendMessage(msg);
                } else {
                    Message msg = ArticleListHandler.obtainMessage();
                    msg.what = 2;
                    ArticleListHandler.sendMessage(msg);
                    Log.d("ArticleException", e.getMessage());
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
                    articleAdapter.setArticles(articles);
                    articleAdapter.notifyDataSetChanged();

                    break;
                case 1:
                    articles = (ArrayList<Article>)msg.obj;
                    articleAdapter.setArticles(articles);
                    articleAdapter.notifyDataSetChanged();
                    Util.myToast(MainActivity.this,"刷新成功");
                    break;
                case 2:
                    Util.myToast(MainActivity.this,"刷新失败，请检查网络");
                default:
                    break;
            }

        }
    };



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_slide:
                slidingMenu.showMenu();
                leftMenu.startAnim();
                break;
            case R.id.right_slide:
                slidingMenu.showSecondaryMenu();
                rightMenu.startAnim();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing() || slidingMenu.isSecondaryMenuShowing()) {
            slidingMenu.showContent();
        } else {
            if (System.currentTimeMillis() - mLastClickTime <= 2000L) {
                super.onBackPressed();
            } else {
                mLastClickTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }

        }

    }
}