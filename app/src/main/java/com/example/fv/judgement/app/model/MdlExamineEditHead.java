package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class MdlExamineEditHead extends BaseModel
{
    private String EmpCName;
    private String groupname;
    private String BeignDate;
    private String EndDate;
    private String StatusTxt;
    private String ExamineDate;
    private String LeavePlanNum;
    private String TypeTxt;
    private String Describe;
    private String BusinessTripPlace;
    private String numcount;;
    private String U_LoginName;
    private String CCAddress;
    /**默认构造方法，JSON等解析时必须要有
     */
    public MdlExamineEditHead() {
        //default
    }
    public MdlExamineEditHead(long id) {
        this();
        this.id = id;
    }
    public MdlExamineEditHead(long id, String name) {
        this(id);
    }

    /**
     * 以下getter和setter可以自动生成
     * <br>  eclipse: 右键菜单 > Source > Generate Getters and Setters
     * <br>  android studio: 右键菜单 > Generate > Getter and Setter
     */

    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }

    public String getEmpCName() {
        return EmpCName;
    }

    public void setEmpCName(String empCName) {
        EmpCName = empCName;
    }

    public String getBeignDate() {
        return BeignDate;
    }

    public void setBeignDate(String beignDate) {
        BeignDate = beignDate;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getCCAddress() {
        return CCAddress;
    }

    public void setCCAddress(String CCAddress) {
        this.CCAddress = CCAddress;
    }

    public String getU_LoginName() {
        return U_LoginName;
    }

    public void setU_LoginName(String u_LoginName) {
        U_LoginName = u_LoginName;
    }

    public String getNumcount() {
        return numcount;
    }

    public void setNumcount(String numcount) {
        this.numcount = numcount;
    }

    public String getBusinessTripPlace() {
        return BusinessTripPlace;
    }

    public void setBusinessTripPlace(String businessTripPlace) {
        BusinessTripPlace = businessTripPlace;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getTypeTxt() {
        return TypeTxt;
    }

    public void setTypeTxt(String typeTxt) {
        TypeTxt = typeTxt;
    }

    public String getLeavePlanNum() {
        return LeavePlanNum;
    }

    public void setLeavePlanNum(String leavePlanNum) {
        LeavePlanNum = leavePlanNum;
    }

    public String getStatusTxt() {
        return StatusTxt;
    }

    public void setStatusTxt(String statusTxt) {
        StatusTxt = statusTxt;
    }

    public String getExamineDate() {
        return ExamineDate;
    }

    public void setExamineDate(String examineDate) {
        ExamineDate = examineDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}

