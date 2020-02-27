package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class BusinessTripDetailModel extends BaseModel
{
    private String BusinessTripID;//主键id
    private String BusinessTripStartTime;//开始时间
    private String BusinessTripEndTime;//结束时间
    private String BusinessTripNum;//天数
    private String BusinessTripReason;//缘由
    private String AnnexName;//图片
    private String BusinessTripPlace;//地点

    @Override
    protected boolean isCorrect() {
    return false;
    }

    public String getBusinessTripID() {
        return BusinessTripID;
    }

    public void setBusinessTripID(String businessTripID) {
        BusinessTripID = businessTripID;
    }

    public String getBusinessTripStartTime() {
        return BusinessTripStartTime;
    }

    public void setBusinessTripStartTime(String businessTripStartTime) {
        BusinessTripStartTime = businessTripStartTime;
    }

    public String getBusinessTripEndTime() {
        return BusinessTripEndTime;
    }

    public void setBusinessTripEndTime(String businessTripEndTime) {
        BusinessTripEndTime = businessTripEndTime;
    }

    public String getBusinessTripNum() {
        return BusinessTripNum;
    }

    public void setBusinessTripNum(String businessTripNum) {
        BusinessTripNum = businessTripNum;
    }

    public String getBusinessTripReason() {
        return BusinessTripReason;
    }

    public void setBusinessTripReason(String businessTripReason) {
        BusinessTripReason = businessTripReason;
    }

    public String getAnnexName() {
        return AnnexName;
    }

    public void setAnnexName(String annexName) {
        AnnexName = annexName;
    }

    public String getBusinessTripPlace() {
        return BusinessTripPlace;
    }

    public void setBusinessTripPlace(String businessTripPlace) {
        BusinessTripPlace = businessTripPlace;
    }
}
