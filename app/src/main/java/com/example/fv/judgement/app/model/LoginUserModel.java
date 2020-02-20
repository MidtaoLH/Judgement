package com.example.fv.judgement.app.model;

public class LoginUserModel {

    //
    public String flag;

    public String id;

    private String code;

    private String name;

    private String EmpID;

    private String Groupid;

    private String GroupName;

    private String UserNO;

    private String UserHour;

    private String IsNotice;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }

    public String getGroupid() {
        return Groupid;
    }

    public void setGroupid(String groupid) {
        Groupid = groupid;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getUserNO() {
        return UserNO;
    }

    public void setUserNO(String userNO) {
        UserNO = userNO;
    }

    public String getUserHour() {
        return UserHour;
    }

    public void setUserHour(String userHour) {
        UserHour = userHour;
    }

    public String getIsNotice() {
        return IsNotice;
    }

    public void setIsNotice(String isNotice) {
        IsNotice = isNotice;
    }
}
