package com.example.fv.judgement.app.model;

public class Group {

    public Group(String Code,String Name){
        this.setCode(Code);
        this.setName(Name);
    }

    public String Code;
    public String Name;

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

    //必须要重写，不然绑定spinner会有问题
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Name;
    }
}
