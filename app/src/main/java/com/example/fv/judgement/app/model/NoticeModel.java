package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class NoticeModel extends BaseModel {

   public String NewsTheme;

    public String NewsContent;

    public String NewsID;

    public String NewsDate;

    public String G_CName;

    public String getNewsTheme() {
        return NewsTheme;
    }

    public void setNewsTheme(String newsTheme) {
        NewsTheme = newsTheme;
    }

    public String getNewsContent() {
        return NewsContent;
    }

    public void setNewsContent(String newsContent) {
        NewsContent = newsContent;
    }

    public String getNewsID() {
        return NewsID;
    }

    public void setNewsID(String newsID) {
        NewsID = newsID;
    }

    public String getNewsDate() {
        return NewsDate;
    }

    public void setNewsDate(String newsDate) {
        NewsDate = newsDate;
    }

    public String getG_CName() {
        return G_CName;
    }

    public void setG_CName(String g_CName) {
        G_CName = g_CName;
    }

    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }
}
