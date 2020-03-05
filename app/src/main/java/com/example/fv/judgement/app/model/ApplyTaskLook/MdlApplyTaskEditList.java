package com.example.fv.judgement.app.model.ApplyTaskLook;

import zuo.biao.library.base.BaseModel;

public class MdlApplyTaskEditList extends BaseModel
{
    private static final long serialVersionUID = 1L;

    /**默认构造方法，JSON等解析时必须要有
     */

    public MdlApplyTaskEditList() {
        //default
    }
    public MdlApplyTaskEditList(long id) {
        this();
        this.id = id;
    }
    public MdlApplyTaskEditList(long id, String name) {
        this(id);
    }
    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }

    //"Table1":[{"TaskInstanceID":739,"TaskNodeOperateType":"2","TaskAuditeStatus":"1","ProcessStutas":"3",
    //"name":"叶明","groupname":"中道益通","Level":1,"levelname":"一级","TaskDate":null,"Remark":null,"U_LoginName":"yeming"}],
    //
    //
    //"Table2":[]}

    //请假承认列表
    private String TaskInstanceID;
    private String TaskNodeOperateType;
    private String TaskAuditeStatus;
    private String ProcessStutas;
    private String name;
    private String groupname;
    private String Level;
    private String levelname;
    private String TaskDate;
    private String Remark;
    private String U_LoginName;

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

    public String getTaskAuditeStatus() {
        return TaskAuditeStatus;
    }

    public void setTaskAuditeStatus(String taskAuditeStatus) {
        TaskAuditeStatus = taskAuditeStatus;
    }

    public String getProcessStutas() {
        return ProcessStutas;
    }

    public void setProcessStutas(String processStutas) {
        ProcessStutas = processStutas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getTaskDate() {
        return TaskDate;
    }

    public void setTaskDate(String taskDate) {
        TaskDate = taskDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getU_LoginName() {
        return U_LoginName;
    }

    public void setU_LoginName(String u_LoginName) {
        U_LoginName = u_LoginName;
    }
}

