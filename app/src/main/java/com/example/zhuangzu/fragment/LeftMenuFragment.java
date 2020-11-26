package com.example.zhuangzu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;

import com.example.zhuangzu.R;
import com.example.zhuangzu.databinding.FragmentLeftMenuBinding;


public class LeftMenuFragment extends Fragment implements View.OnClickListener {
    private FragmentLeftMenuBinding leftMenuBinding;

    public LeftMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        leftMenuBinding = FragmentLeftMenuBinding.inflate(inflater, container, false);
        leftMenuBinding.homePageTv.setOnClickListener(this);
        leftMenuBinding.rightSlideClose.setOnClickListener(this);
        return leftMenuBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_slide_close:
                getActivity().onBackPressed();
                break;
            case R.id.home_page_tv:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }


    public void startAnim() {
        startIconAnim(leftMenuBinding.search);
        startIconAnim(leftMenuBinding.rightSlideClose);

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