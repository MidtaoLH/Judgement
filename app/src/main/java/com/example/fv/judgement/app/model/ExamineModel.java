package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class ExamineModel extends BaseModel
{
    private static final long serialVersionUID = 1L;

    public static final int SEX_MAIL = 0;
    public static final int SEX_FEMALE = 1;
    public static final int SEX_UNKNOWN = 2;

    private String caseApplyMan; //头像
    private String caseName; //名字
    private String documentName; //电话号码
    private String caseTypeTxt; //标签

    /**默认构造方法，JSON等解析时必须要有
     */
    public ExamineModel() {
        //default
    }
    public ExamineModel(long id) {
        this();
        this.id = id;
    }
    public ExamineModel(long id, String name) {
        this(id);
        this.caseApplyMan = name;
    }

    /**
     * 以下getter和setter可以自动生成
     * <br>  eclipse: 右键菜单 > Source > Generate Getters and Setters
     * <br>  android studio: 右键菜单 > Generate > Getter and Setter
     */

//    public String getTag() {
//        return tag;
//    }
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//    public boolean getStarred() {
//        return starred;
//    }
//    public void setStarred(boolean starred) {
//        this.starred = starred;
//    }

    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }
    public String getCaseTypeTxt() {
        return caseTypeTxt;
    }
    public void setCaseTypeTxt(String caseTypeTxt) {
        this.caseTypeTxt = caseTypeTxt;
    }
    public String getDocumentName() {
        return documentName;
    }
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    public String getCaseName() {
        return caseName;
    }
    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
    public String getCaseApplyMan() {
        return caseApplyMan;
    }
    public void setCaseApplyMan(String caseApplyMan) {
        this.caseApplyMan = caseApplyMan;
    }
}

