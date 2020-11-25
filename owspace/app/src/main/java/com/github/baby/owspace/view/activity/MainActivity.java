package com.github.baby.owspace.view.activity;

import  android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.baby.owspace.R;
import com.github.baby.owspace.app.OwspaceApplication;
import com.github.baby.owspace.di.components.DaggerMainComponent;
import com.github.baby.owspace.di.modules.MainModule;
import com.github.baby.owspace.model.entity.Event;
import com.github.baby.owspace.model.entity.Item;
import com.github.baby.owspace.presenter.MainContract;
import com.github.baby.owspace.presenter.MainPresenter;
import com.github.baby.owspace.util.AppUtil;
import com.github.baby.owspace.util.PreferenceUtils;
import com.github.baby.owspace.util.TimeUtil;
import com.github.baby.owspace.util.tool.RxBus;
import com.github.baby.owspace.view.adapter.VerticalPagerAdapter;
import com.github.baby.owspace.view.fragment.LeftMenuFragment;
import com.github.baby.owspace.view.fragment.RightMenuFragment;
import com.github.baby.owspace.view.widget.LunarDialog;
import com.github.baby.owspace.view.widget.VerticalViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.view_pager)
    VerticalViewPager viewPager;
    private SlidingMenu slidingMenu;
    private LeftMenuFragment leftMenu;
    private RightMenuFragment rightMenu;
    @Inject
    MainPresenter presenter;
    private VerticalPagerAdapter pagerAdapter;

    private int page = 1;
    private boolean isLoading = true;
    private long mLastClickTime;

    private Subscription subscription;

    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMenu();//初始化菜单，主要是提供可滑动窗口
        initPage();//初始化界面
        deviceId = AppUtil.getDeviceId(this);//获得手机的唯一标识码
        String getLunar= PreferenceUtils.getPrefString(this,"getLunar",null);//获取上次时间app启动的日期
        if (!TimeUtil.getDate("yyyyMMdd").equals(getLunar)){//如果登陆日期和上次登陆日期不同，说明是新的一天，需要额外展示当天日期的对话框
            loadRecommend();
        }
        loadData(1, 0, "0", "0");
    }

    private void initMenu() {
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
        subscription = RxBus.getInstance().toObservable(Event.class)
                .subscribe(new Action1<Event>() {
                    @Override
                    public void call(Event event) {
                        slidingMenu.showContent();
                    }
                });
    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    private void initPage() {
        pagerAdapter = new VerticalPagerAdapter(getSupportFragmentManager());
        DaggerMainComponent.builder().
                mainModule(new MainModule(this))
                .netComponent(OwspaceApplication.get(this).getNetComponent())
                .build()
                .inject(this);
        viewPager.setAdapter(pagerAdapter);//此处将展示页面可点击的fragment放置到主滑动页面
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (pagerAdapter.getCount() <= position + 2 && !isLoading) {
                    if (isLoading){
                        Toast.makeText(MainActivity.this,"正在努力加载...",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Logger.i("page=" + page + ",getLastItemId=" + pagerAdapter.getLastItemId());
                    loadData(page, 0, pagerAdapter.getLastItemId(), pagerAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadData(int page, int mode, String pageId, String createTime) {
        isLoading = true;
        presenter.getListByPage(page, mode, pageId, deviceId, createTime);
    }
    private void loadRecommend(){
        presenter.getRecommend(deviceId);
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

    @OnClick({R.id.left_slide, R.id.right_slide})
    public void onClick(View view) {
        switch (view.getId()) {
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
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showNoMore() {
        Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListUI(List<Item> itemList) {
        isLoading = false;
        pagerAdapter.setDataList(itemList);
        page++;
    }

    @Override
    public void showOnFailure() {
        Toast.makeText(this, "加载数据失败，请检查您的网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLunar(String content) {
        Logger.e("showLunar:"+content);
        PreferenceUtils.setPrefString(this,"getLunar",TimeUtil.getDate("yyyyMMdd"));
        LunarDialog lunarDialog = new LunarDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_lunar,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_iv);
        Glide.with(this).load(content).into(imageView);//将二进制码通过glide展示到指定位置
        lunarDialog.setContentView(view);
        lunarDialog.show();//对话框展示当天日期图

    }

    @Override
    protected void onDestroy() {
        if (subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
