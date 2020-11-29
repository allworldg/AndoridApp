package com.example.zhuangzu.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Util {
    public static void myLog(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
