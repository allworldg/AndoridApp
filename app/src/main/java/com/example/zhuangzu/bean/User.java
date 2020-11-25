package com.example.zhuangzu.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    String nickName;

    public User() {
    }

    public User(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


}
