package com.example.zhuangzu.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.zhuangzu.databinding.ActivitySortBinding;

public class SortActivity extends AppCompatActivity {
   ActivitySortBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySortBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


    public static void actionStart(Context context,String type){
        Intent intent = new Intent(context,SortActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
}