package com.example.zhuangzu.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    String nickName;
    BmobFile headPicture;

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    String testString;
    public BmobFile getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(BmobFile headPicture) {
        this.headPicture = headPicture;
    }


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
