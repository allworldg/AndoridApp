package com.example.zhuangzu.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.zhuangzu.R;
import com.example.zhuangzu.databinding.FragmentLeftMenuBinding;
import com.example.zhuangzu.view.activity.SortActivity;

import java.util.ArrayList;


public class LeftMenuFragment extends Fragment implements View.OnClickListener {
    private FragmentLeftMenuBinding binding;
    private ArrayList<View> viewArrayList;
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
        binding = FragmentLeftMenuBinding.inflate(inflater, container, false);
        binding.homePageTv.setOnClickListener(this);
        binding.rightSlideClose.setOnClickListener(this);
        binding.viewTv.setOnClickListener(this);
        binding.foodTv.setOnClickListener(this);
        binding.cultureTv.setOnClickListener(this);
        viewArrayList = new ArrayList<>();
        loadView();
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_tv:
                SortActivity.actionStart(getActivity(),"1");
                break;
            case R.id.culture_tv:
                SortActivity.actionStart(getActivity(),"2");
                break;
            case R.id.food_tv:
                SortActivity.actionStart(getActivity(),"3");
                break;
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
    private void loadView() {
        viewArrayList.add(binding.homePageTv);
        viewArrayList.add(binding.cultureTv);
        viewArrayList.add(binding.foodTv);
        viewArrayList.add(binding.viewTv);

    }

    public void startAnim() {
        startIconAnim(binding.search);
        startIconAnim(binding.rightSlideClose);
        startColumnAnim();
    }
    private void startColumnAnim() {
        TranslateAnimation localTranslateAnimation = new TranslateAnimation(0F, 0.0F, 0.0F, 0.0F);
        localTranslateAnimation.setDuration(700L);
        for (int j=0;j<viewArrayList.size();j++){
            View localView = this.viewArrayList.get(j);
            localView.startAnimation(localTranslateAnimation);
            localTranslateAnimation = new TranslateAnimation(j * -35,0.0F, 0.0F, 0.0F);
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