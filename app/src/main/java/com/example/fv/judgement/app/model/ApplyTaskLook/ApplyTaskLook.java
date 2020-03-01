package com.example.fv.judgement.app.model.ApplyTaskLook;

import zuo.biao.library.base.BaseModel;

public class ApplyTaskLook extends BaseModel
{
    private static final long serialVersionUID = 1L;

    /**默认构造方法，JSON等解析时必须要有
     */

    public ApplyTaskLook() {
        //default
    }
    public ApplyTaskLook(long id) {
        this();
        this.id = id;
    }
    public ApplyTaskLook(long id, String name) {
        this(id);
    }
    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }
//    [{"ProcessInstanceID":216,"ApplyMan":"test","ApplyManName":"测试号","ApplyGroupName":"开发部",
//   "ApplyDate":"2020-01-20","ApplyAmount":1.000000,"strattime":"2020-01-19","endtime":"2020-01-19",
//    "HistoryType":"出差","ProcDescribe":"32","ProcStatus":"待承认","DocumentName":"出差","CCAddress":"T"}]
    private String ProcessInstanceID;
    private String ApplyMan;
    private String ApplyManName;
    private String ApplyGroupName;
    private String ApplyDate;
    private String ApplyAmount;
    private String strattime;
    private String endtime;
    private String HistoryType;
    private String ProcDescribe;
    private String ProcStatus;
    private String DocumentName;
    private String CCAddress;

    public String getProcessInstanceID() {
        return ProcessInstanceID;
    }

    public void setProcessInstanceID(String processInstanceID) {
        ProcessInstanceID = processInstanceID;
    }

    public String getApplyMan() {
        return ApplyMan;
    }

    public void setApplyMan(String applyMan) {
        ApplyMan = applyMan;
    }

    public String getApplyManName() {
        return ApplyManName;
    }

    public void setApplyManName(String applyManName) {
        ApplyManName = applyManName;
    }

    public String getApplyGroupName() {
        return ApplyGroupName;
    }

    public void setApplyGroupName(String applyGroupName) {
        ApplyGroupName = applyGroupName;
    }

    public String getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(String applyDate) {
        ApplyDate = applyDate;
    }

    public String getApplyAmount() {
        return ApplyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        ApplyAmount = applyAmount;
    }

    public String getStrattime() {
        return strattime;
    }

    public void setStrattime(String strattime) {
        this.strattime = strattime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getHistoryType() {
        return HistoryType;
    }

    public void setHistoryType(String historyType) {
        HistoryType = historyType;
    }

    public String getProcDescribe() {
        return ProcDescribe;
    }

    public void setProcDescribe(String procDescribe) {
        ProcDescribe = procDescribe;
    }

    public String getProcStatus() {
        return ProcStatus;
    }

    public void setProcStatus(String procStatus) {
        ProcStatus = procStatus;
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }

    public String getCCAddress() {
        return CCAddress;
    }

    public void setCCAddress(String CCAddress) {
        this.CCAddress = CCAddress;
    }
}

