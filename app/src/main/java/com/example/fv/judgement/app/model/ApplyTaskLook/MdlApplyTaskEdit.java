package com.example.fv.judgement.app.model.ApplyTaskLook;

import zuo.biao.library.base.BaseModel;

public class MdlApplyTaskEdit extends BaseModel
{
    private static final long serialVersionUID = 1L;

    /**默认构造方法，JSON等解析时必须要有
     */

    public MdlApplyTaskEdit() {
        //default
    }
    public MdlApplyTaskEdit(long id) {
        this();
        this.id = id;
    }
    public MdlApplyTaskEdit(long id, String name) {
        this(id);
    }
    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }
    //{"Table":[{"LeaveID":78,"EmpID_FK":19,"EmpName":"叶明","ApplyDate":"2020-02-28","GroupID_FK":1,"G_CName":"中道益通","ProcessApplyCode":"20200185QJ","ProcessVersion":"V01",
    //"CaseName":"叶明的请假申请","AwardType":"1","LeaveTypeTxt":"事假","PlanStartTime":"2020-01-16","PlanEndTime":"2020-01-16","TimePlanNum":"2.0","StartTime":null,
    //"EndTime":null,"TimeNum":null,"ProcessStutas":"3","ProcessStutasTxt":"待承认","ProcessFinishCode":null,"MustCutDate":null,"CaseDescribe":"3","Remark":"",
    //"DiffReason":null,"U_LoginName":"yeming"}],
    //
    //"Table1":[{"TaskInstanceID":739,"TaskNodeOperateType":"2","TaskAuditeStatus":"1","ProcessStutas":"3",
    //"name":"叶明","groupname":"中道益通","Level":1,"levelname":"一级","TaskDate":null,"Remark":null,"U_LoginName":"yeming"}],
    //
    //
    //"Table2":[]}

    //请假头部
    private String EmpName;
    private String ApplyDate;
    private String G_CName;
    private String CaseName;
    private String LeaveTypeTxt;
    private String PlanStartTime;
    private String PlanEndTime;
    private String TimePlanNum;
    private String U_LoginName;
    private String ProcessStutasTxt;
    private String LeaveID;

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getG_CName() {
        return G_CName;
    }

    public void setG_CName(String g_CName) {
        G_CName = g_CName;
    }

    public String getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(String applyDate) {
        ApplyDate = applyDate;
    }

    public String getCaseName() {
        return CaseName;
    }

    public void setCaseName(String caseName) {
        CaseName = caseName;
    }

    public String getPlanStartTime() {
        return PlanStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        PlanStartTime = planStartTime;
    }

    public String getLeaveTypeTxt() {
        return LeaveTypeTxt;
    }

    public void setLeaveTypeTxt(String leaveTypeTxt) {
        LeaveTypeTxt = leaveTypeTxt;
    }

    public String getPlanEndTime() {
        return PlanEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        PlanEndTime = planEndTime;
    }


    public String getTimePlanNum() {
        return TimePlanNum;
    }

    public void setTimePlanNum(String timePlanNum) {
        TimePlanNum = timePlanNum;
    }

    public String getU_LoginName() {
        return U_LoginName;
    }

    public void setU_LoginName(String u_LoginName) {
        U_LoginName = u_LoginName;
    }

    public String getProcessStutasTxt() {
        return ProcessStutasTxt;
    }

    public void setProcessStutasTxt(String processStutasTxt) {
        ProcessStutasTxt = processStutasTxt;
    }

    public String getLeaveID() {
        return LeaveID;
    }

    public void setLeaveID(String leaveID) {
        LeaveID = leaveID;
    }
}

