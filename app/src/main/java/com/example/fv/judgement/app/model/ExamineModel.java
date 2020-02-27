package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class ExamineModel extends BaseModel
{
    private static final long serialVersionUID = 1L;

    public static final int SEX_MAIL = 0;
    public static final int SEX_FEMALE = 1;
    public static final int SEX_UNKNOWN = 2;

    private String CaseApplyMan;
    private String CaseName;
    private String DocumentName;
    private String CaseTypeTxt;
    private String ApplyManPhoto;
    private String BeignDate;
    private String EndDate;
    private String CaseDate;
    private String CaseStatusTxt;
    private String  AidFK;
    private String  TaskInstanceID;
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
        this.CaseApplyMan = name;
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

    public String getCaseApplyMan() {
        return CaseApplyMan;
    }

    public void setCaseApplyMan(String caseApplyMan) {
        CaseApplyMan = caseApplyMan;
    }

    public String getCaseName() {
        return CaseName;
    }

    public void setCaseName(String caseName) {
        CaseName = caseName;
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }

    public String getCaseTypeTxt() {
        return CaseTypeTxt;
    }

    public void setCaseTypeTxt(String caseTypeTxt) {
        CaseTypeTxt = caseTypeTxt;
    }

    public String getApplyManPhoto() {
        return ApplyManPhoto;
    }

    public void setApplyManPhoto(String applyManPhoto) {
        ApplyManPhoto = applyManPhoto;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getBeginDate() {
        return BeignDate;
    }

    public void setBeginDate(String beginDate) {
        BeignDate = beginDate;
    }

    public String getCaseDate() {
        return CaseDate;
    }

    public void setCaseDate(String caseDate) {
        CaseDate = caseDate;
    }

    public String getCaseStatusTxt() {
        return CaseStatusTxt;
    }

    public void setCaseStatusTxt(String caseStatusTxt) {
        CaseStatusTxt = caseStatusTxt;
    }

    public String getAidFK() {
        return AidFK;
    }

    public void setAidFK(String aidFK) {
        AidFK = aidFK;
    }

    public String getTaskInstanceID() {
        return TaskInstanceID;
    }

    public void setTaskInstanceID(String taskInstanceID) {
        TaskInstanceID = taskInstanceID;
    }
}

