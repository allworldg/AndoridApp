package com.example.zhuangzu.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.bumptech.glide.Glide;
import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.databinding.FragmentRightMenuBinding;
import com.example.zhuangzu.view.activity.LikeActivity;
import com.example.zhuangzu.view.activity.LoginActivity;
import com.example.zhuangzu.view.activity.SettingActivity;
import com.example.zhuangzu.view.activity.UserInformationActivity;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;


public class RightMenuFragment extends Fragment implements View.OnClickListener {
    private FragmentRightMenuBinding binding;
    private ArrayList<View> mViewList = new ArrayList<>();
    private static User user;
    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG","on resume");
        if(haveLogin(getActivity())){
            binding.loginTv.setVisibility(View.GONE);
            binding.tvName.setText(user.getNickName());
            binding.headPicIv.setOnClickListener(this);
            if(user.getHeadPicture()!=null){
                Util.show(getActivity(),user.getHeadPicture().getFileUrl(), binding.headPicIv);

            }else{
                Glide.with(getActivity()).load(R.drawable.avater_default).into(binding.headPicIv);
            }
        }else{
            binding.tvName.setText(" ");
            binding.loginTv.setVisibility(View.VISIBLE);
            binding.headPicIv.setOnClickListener(null);
            Glide.with(getActivity()).load(R.drawable.avater_default).into(binding.headPicIv);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRightMenuBinding.inflate(inflater, container, false);
        loadView();
        binding.rightSlideClose.setOnClickListener(this);
        binding.loginTv.setOnClickListener(this);
        binding.setting.setOnClickListener(this);
        binding.favoritesTv.setOnClickListener(this);
        if(haveLogin(getActivity())){
            binding.loginTv.setVisibility(View.GONE);
            binding.tvName.setText(user.getNickName());
            if(user.getHeadPicture()!=null){
                Util.show(getActivity(),user.getHeadPicture().getFileUrl(), binding.headPicIv);
            }
        }
        Log.d("TAG","on createview");
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_slide_close:
                getActivity().onBackPressed();
                break;
            case R.id.login_tv:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                Intent intent1 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.headPic_iv:
                Intent intent2 = new Intent(getActivity(), UserInformationActivity.class);
                startActivity(intent2);
                break;
            case R.id.favorites_tv:
                if(!BmobUser.isLogin()){
                    Util.myToast(getActivity(),"请先登陆");
                }else{
                    LikeActivity.actionStart(getActivity());
                }
                break;
            default:
                break;
        }
    }

    private void loadView() {
        mViewList.add(binding.loginTv);
        mViewList.add(binding.favoritesTv);
        mViewList.add(binding.downloadTv);
        mViewList.add(binding.noteTv);
    }

    public void startAnim() {
        startIconAnim(binding.rightSlideClose);
        startIconAnim(binding.setting);
        startColumnAnim();
    }

    private void startColumnAnim() {
        TranslateAnimation localTranslateAnimation = new TranslateAnimation(0F, 0.0F, 0.0F, 0.0F);
        localTranslateAnimation.setDuration(700L);
        for (int j = 0; j < mViewList.size(); j++) {
            View localView = this.mViewList.get(j);
            localView.startAnimation(localTranslateAnimation);
            localTranslateAnimation = new TranslateAnimation(j * 35, 0.0F, 0.0F, 0.0F);
            localTranslateAnimation.setDuration(700L);
        }
    }

    private void startIconAnim(View paramView) {
        ScaleAnimation localScaleAnimation = new ScaleAnimation(0.1F, 1.0F, 0.1F, 1.0F, paramView.getWidth() / 2, paramView.getHeight() / 2);
        localScaleAnimation.setDuration(1000L);
        paramView.startAnimation(localScaleAnimation);
        float f1 = paramView.getWidth() / 2;
        float f2 = paramView.getHeight() / 2;
        localScaleAnimation = new ScaleAnimation(1.0F, 0.5F, 1.0F, 0.5F, f1, f2);
        localScaleAnimation.setInterpolator(new BounceInterpolator());
    }

    /**
     * 测试当前是否有用户登陆
     * @param context
     */
    public static boolean haveLogin(Context context){
        if(BmobUser.isLogin()){

            user = BmobUser.getCurrentUser(User.class);
            return true;
        }
        return false;
    }

}