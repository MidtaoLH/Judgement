package com.example.fv.judgement.app.model.ApplyTaskLook;

import zuo.biao.library.base.BaseModel;

public class ApplyTaskLookList extends BaseModel
{
    private static final long serialVersionUID = 1L;

    /**默认构造方法，JSON等解析时必须要有
     */

    public ApplyTaskLookList() {
        //default
    }
    public ApplyTaskLookList(long id) {
        this();
        this.id = id;
    }
    public ApplyTaskLookList(long id, String name) {
        this(id);
    }
    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }
    //[{"TaskInstanceID":485,"TaskEmpCName":"(代)李赫","TaskNodeLevel":1,
    // "TaskNodeLevelNM":"一级","TaskEmpGroupName":"开发部","TaskAuditeDate":null,
    //"Remark":null,"TaskAuditeStatusNM":"待承认","UserCode":"lihe"},

    private String TaskInstanceID;
    private String TaskEmpCName;
    private String TaskNodeLevel;
    private String TaskNodeLevelNM;
    private String TaskEmpGroupName;
    private String TaskAuditeDate;
    private String Remark;
    private String TaskAuditeStatusNM;
    private String UserCode;

    public String getTaskInstanceID() {
        return TaskInstanceID;
    }

    public void setTaskInstanceID(String taskInstanceID) {
        TaskInstanceID = taskInstanceID;
    }

    public String getTaskEmpCName() {
        return TaskEmpCName;
    }

    public void setTaskEmpCName(String taskEmpCName) {
        TaskEmpCName = taskEmpCName;
    }

    public String getTaskNodeLevel() {
        return TaskNodeLevel;
    }

    public void setTaskNodeLevel(String taskNodeLevel) {
        TaskNodeLevel = taskNodeLevel;
    }

    public String getTaskNodeLevelNM() {
        return TaskNodeLevelNM;
    }

    public void setTaskNodeLevelNM(String taskNodeLevelNM) {
        TaskNodeLevelNM = taskNodeLevelNM;
    }

    public String getTaskEmpGroupName() {
        return TaskEmpGroupName;
    }

    public void setTaskEmpGroupName(String taskEmpGroupName) {
        TaskEmpGroupName = taskEmpGroupName;
    }

    public String getTaskAuditeDate() {
        return TaskAuditeDate;
    }

    public void setTaskAuditeDate(String taskAuditeDate) {
        TaskAuditeDate = taskAuditeDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTaskAuditeStatusNM() {
        return TaskAuditeStatusNM;
    }

    public void setTaskAuditeStatusNM(String taskAuditeStatusNM) {
        TaskAuditeStatusNM = taskAuditeStatusNM;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }
}

