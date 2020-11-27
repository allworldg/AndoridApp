package com.example.zhuangzu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.zhuangzu.R;
import com.example.zhuangzu.databinding.FragmentRightMenuBinding;
import com.example.zhuangzu.databinding.RightMenuBinding;

import java.util.ArrayList;


public class RightMenuFragment extends Fragment implements View.OnClickListener {
    private FragmentRightMenuBinding fragmentRightMenuBinding;
    private ArrayList<View> mViewList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRightMenuBinding = FragmentRightMenuBinding.inflate(inflater, container, false);
        loadView();
        fragmentRightMenuBinding.rightSlideClose.setOnClickListener(this);
        return fragmentRightMenuBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_slide_close:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void loadView() {
        mViewList.add(fragmentRightMenuBinding.notificationTv);
        mViewList.add(fragmentRightMenuBinding.favoritesTv);
        mViewList.add(fragmentRightMenuBinding.downloadTv);
        mViewList.add(fragmentRightMenuBinding.noteTv);
    }

    public void startAnim() {
        startIconAnim(fragmentRightMenuBinding.rightSlideClose);
        startIconAnim(fragmentRightMenuBinding.setting);
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

}