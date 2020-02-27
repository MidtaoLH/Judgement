package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class BusinessTripListModel extends BaseModel {
    /**默认构造方法，JSON等解析时必须要有
     */
    public BusinessTripListModel() {
        //default
    }
    public BusinessTripListModel(long id) {
        this();
        this.id = id;
    }
    public BusinessTripListModel(long id, String name) {
        this(id);
        this.U_LoginName = name;
    }

    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }
    private String rowId;
    private String UserID;

    private String U_LoginName;
    public String getU_LoginName() {
        return U_LoginName;
    }
    public void setU_LoginName(String u_LoginName) {
        U_LoginName = u_LoginName;
    }

    private String PicID;
    public String getPicID() {return PicID; }
    public void setPicID(String picID) {PicID = picID; }

    private String AidFK;
    private String CaseApplyMan;
    private String CaseName;
    private String DocumentID_FK;
    private String DocumentName;
    private String CaseTypeTxt;
    private String CaseDate;
    private String BeignDate;
    private String EndDate;
    private String CaseStatusTxt;
    private String CaseApplyCode;
    private String CaseVersion;
    private String ApplyDateTime;
    private String ApplyManPhoto;

    public String getAidFK() {
        return AidFK;
    }

    public void setAidFK(String aidFK) {
        AidFK = aidFK;
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

    public String getDocumentID_FK() {
        return DocumentID_FK;
    }

    public void setDocumentID_FK(String documentID_FK) {
        DocumentID_FK = documentID_FK;
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

    public String getCaseDate() {
        return CaseDate;
    }

    public void setCaseDate(String caseDate) {
        CaseDate = caseDate;
    }

    public String getBeignDate() {
        return BeignDate;
    }

    public void setBeignDate(String beignDate) {
        BeignDate = beignDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getCaseStatusTxt() {
        return CaseStatusTxt;
    }

    public void setCaseStatusTxt(String caseStatusTxt) {
        CaseStatusTxt = caseStatusTxt;
    }

    public String getCaseApplyCode() {
        return CaseApplyCode;
    }

    public void setCaseApplyCode(String caseApplyCode) {
        CaseApplyCode = caseApplyCode;
    }

    public String getCaseVersion() {
        return CaseVersion;
    }

    public void setCaseVersion(String caseVersion) {
        CaseVersion = caseVersion;
    }

    public String getApplyDateTime() {
        return ApplyDateTime;
    }

    public void setApplyDateTime(String applyDateTime) {
        ApplyDateTime = applyDateTime;
    }

    public String getApplyManPhoto() {
        return ApplyManPhoto;
    }

    public void setApplyManPhoto(String applyManPhoto) {
        ApplyManPhoto = applyManPhoto;
    }
}

