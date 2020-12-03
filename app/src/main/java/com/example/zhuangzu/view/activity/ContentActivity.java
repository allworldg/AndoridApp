package com.example.zhuangzu.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.example.zhuangzu.R;
import com.example.zhuangzu.databinding.ActivityContentBinding;

public class ContentActivity extends AppCompatActivity {
    ActivityContentBinding binding;
    private String detailData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        detailData = getIntent().getStringExtra("url");
        binding.wbview.loadUrl(detailData);
        binding.wbview.setWebViewClient(new WebViewClient());
        WebSettings webSettings = binding.wbview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
    }
}