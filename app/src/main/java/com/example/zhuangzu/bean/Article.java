package com.example.zhuangzu.bean;

import cn.bmob.v3.BmobArticle;
import cn.bmob.v3.datatype.BmobFile;

public class Article  extends BmobArticle {
    private String author;//作者名字
    private BmobFile picture;//；封面图
    private String  shortContent;//封面简介

    public Article(){}
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }
}
