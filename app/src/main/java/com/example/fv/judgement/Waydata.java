package com.example.fv.judgement;

import android.app.Application;

public class Waydata extends Application {

    public String Index;

    public String levelname;

    public String name;

    private String nameid;

    private String groupname;

    private String groupid;

    private String englishname;

    private String level;

    private String editflag;

    private String Condition;

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEditflag() {
        return editflag;
    }

    public void setEditflag(String editflag) {
        this.editflag = editflag;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    @Override
    public void onCreate(){

         Index = "0";

        levelname = "";

        name = "";

         nameid = "";

         groupname = "";

         groupid = "";

        englishname = "";

         level = "";

         editflag = "";

         Condition = "";

        super.onCreate();
    }
}
