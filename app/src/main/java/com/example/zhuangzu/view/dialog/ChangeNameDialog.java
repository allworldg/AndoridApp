package com.example.zhuangzu.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuangzu.R;
import com.example.zhuangzu.Util.Util;
import com.example.zhuangzu.bean.User;
import com.example.zhuangzu.view.activity.MainActivity;
import com.example.zhuangzu.view.activity.UserInformationActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * 简单的对话框
 * Created by MH on 2016/6/15.
 */
public class ChangeNameDialog extends Dialog implements View.OnClickListener {
   OnSureClickListener onSureClickListener;

    public ChangeNameDialog(Context context,OnSureClickListener onSureClickListener) {

        // 注意，在此处设置样式
        super(context, R.style.CustomDialog);

        // 设置我们的布局到dialog中
        setContentView(R.layout.dialog_changename);
        this.onSureClickListener = onSureClickListener;
        // 初始化布局
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_cancel:
                // 对应的点击事件
                this.dismiss();
                break;
            case R.id.tv_ok:
                EditText newUserName = findViewById(R.id.et_newUserName);
                String newName = newUserName.getText().toString();
                onSureClickListener.getText(newName);
                dismiss();
                break;
            default:
                break;
        }
    }
    public interface OnSureClickListener {
        void getText(String string); // 声明获取EditText中数据的接口
    }

}