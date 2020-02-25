package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class LeaveTypeModel {

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String Code;//请假主键Code
    private String Name;//请假类型Name
}
