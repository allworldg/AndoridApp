package com.example.zhuangzu.Util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.zhuangzu.R;

public class Util {
    public static void myToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, String uri, ImageView view){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.avater_default);
        if(uri.startsWith("http://")){
            uri = uri.replace("http://","https://");
        }
        Glide.with(context).load(uri).apply(requestOptions).into(view);
    }
}
