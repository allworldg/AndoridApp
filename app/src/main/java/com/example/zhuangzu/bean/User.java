package com.example.zhuangzu.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {
    String nickName;//昵称
    BmobFile headPicture;//头像
    BmobRelation mlike;//收藏的任务，一对多关系

    public BmobRelation getMlike() {
        return mlike;
    }

    public void setMlike(BmobRelation mlike) {
        this.mlike = mlike;
    }

    public BmobFile getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(BmobFile headPicture) {
        this.headPicture = headPicture;
    }



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


}
