package com.example.zhuangzu.bean;

import cn.bmob.v3.datatype.BmobFile;

public class Article {
    public Article(){}
    private String title;
    private BmobFile picture;
    private String authorName;
    private String shortContent;//在外部简短的介绍内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private int type;//文章种类，分为文化，美食，景点，1，2，3来分类判断
    private String content;//正文
}
