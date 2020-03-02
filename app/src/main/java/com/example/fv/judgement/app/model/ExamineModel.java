package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class ExamineModel extends BaseModel
{
    private static final long serialVersionUID = 1L;

    public static final int SEX_MAIL = 0;
    public static final int SEX_FEMALE = 1;
    public static final int SEX_UNKNOWN = 2;
// [{"rowId":1,"UserID":75,"LeaveID":null,"AwardID_FK":78,"ApplyDate":"2020.02.28","GroupID":null,
// "G_CName":null,"ProcessInstanceID":145,"ProcessApplyCode":"20200185QJ","ProcessFinishCode":null,
// "ProcessVersion":"V01","LeaveType":"1","LeaveTypeName":"事假","ApplyFileName":"叶明的请假申请",
// "ApplyGroupID_FK":1,"BeginDate":"2020-01-16","EndDate":"2020-01-16","ApplyGroupName":"中道益通",
// "ApplyMan":19,"EmpCName":"叶明","EmpName":null,"CaseName":null,"PlanStartTime":null,"PlanEndTime":null,
// "TimePlanNum":null,"StartTime":null,"EndTime":null,"TimeNum":null,"ProcessStutasTxt":null,"MustCutDate":null,
// "CaseDescribe":null,"Project":null,"AwardType":null,"Remark":null,"DiffReason":null,
// "LeavePlanTime":"20200116-20200116","LeavePlanNum":2.0,"LeaveTime":null,"LeaveNum":null,
// "ProcessStutas":"3","ProcessStutasName":"待承认","ProjectName":null,"U_LoginName":"yeming","Sel_Flag":null}]

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
    private String   TaskNodeOperateType;
    private String   PicID;
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

    public String getTaskNodeOperateType() {
        return TaskNodeOperateType;
    }

    public void setTaskNodeOperateType(String taskNodeOperateType) {
        TaskNodeOperateType = taskNodeOperateType;
    }

    public String getPicID() {
        return PicID;
    }

    public void setPicID(String picID) {
        PicID = picID;
    }
}

