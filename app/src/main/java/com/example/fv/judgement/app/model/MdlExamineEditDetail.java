package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class MdlExamineEditDetail extends BaseModel
{
    private String name;
    private String groupname;
    private String Level;
    private String levelname;
    private String TaskDate;
    private String Remark;
    private String TaskNodeOperateType;
    private String TaskAuditeStatus;
    private String ProcessStutas;
    private String TaskInstanceID;
    private String U_LoginName;;
    /**默认构造方法，JSON等解析时必须要有
     */
    public MdlExamineEditDetail() {
        //default
    }
    public MdlExamineEditDetail(long id) {
        this();
        this.id = id;
    }
    public MdlExamineEditDetail(long id, String name) {
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

    public String getTaskInstanceID() {
        return TaskInstanceID;
    }

    public void setTaskInstanceID(String taskInstanceID) {
        TaskInstanceID = taskInstanceID;
    }

    public String getU_LoginName() {
        return U_LoginName;
    }

    public void setU_LoginName(String u_LoginName) {
        U_LoginName = u_LoginName;
    }
}

