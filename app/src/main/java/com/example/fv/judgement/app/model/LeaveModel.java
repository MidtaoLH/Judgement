package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class LeaveModel  extends BaseModel
{

    private String vatcationid;//请假主键id
    private String vatcationtrpeid;//请假类型id
    private String vatcationtype;//请假类型名称
    private String timestart;//请假开始时间
    private String timesend;//请假结束时间
    private String timesum;//请假时长
    private String vatcationreason;//请假缘由
    private String imagepath;//请假图片

    public String getVatcationid() {
        return vatcationid;
    }

    public void setVatcationid(String vatcationid) {
        this.vatcationid = vatcationid;
    }

    public String getVatcationtrpeid() {
        return vatcationtrpeid;
    }

    public void setVatcationtrpeid(String vatcationtrpeid) {
        this.vatcationtrpeid = vatcationtrpeid;
    }

    public String getVatcationtype() {
        return vatcationtype;
    }

    public void setVatcationtype(String vatcationtrpe) {
        this.vatcationtype = vatcationtrpe;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimesend() {
        return timesend;
    }

    public void setTimesend(String timesend) {
        this.timesend = timesend;
    }

    public String getTimesum() {
        return timesum;
    }

    public void setTimesum(String timesum) {
        this.timesum = timesum;
    }

    public String getVatcationreason() {
        return vatcationreason;
    }

    public void setVatcationreason(String vatcationreason) {
        this.vatcationreason = vatcationreason;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }


    @Override
    protected boolean isCorrect() {
        return false;
    }
}
