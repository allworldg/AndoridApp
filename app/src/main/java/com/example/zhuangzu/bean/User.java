package com.example.zhuangzu.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends   BmobUser {
    String nickName;//昵称
    BmobFile headPicture;//头像
    BmobRelation mLike;//收藏的任务，一对多关系
    public BmobRelation getmLike() {
        return mLike;
    }

    public void setmLike(BmobRelation mLike) {
        this.mLike = mLike;
    }

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
