package com.example.fv.judgement.app.model;

public class Emp {

    public Emp(String GCode,String Code,String Name,String ENO,String ENGLISHNAME){
       this.setGCode(GCode);
        this.setCode(Code);
        this.setName(Name);
        this.setENO(ENO);
        this.setENGLISHNAME(ENGLISHNAME);
    }

    public  String GCode;
    public String Code;
    public String Name;

    public String ENO;
    public String ENGLISHNAME;

    public String getGCode() {
        return GCode;
    }

    public void setGCode(String GCode) {
        this.GCode = GCode;
    }

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

    public String getENO() {
        return ENO;
    }

    public void setENO(String ENO) {
        this.ENO = ENO;
    }

    public String getENGLISHNAME() {
        return ENGLISHNAME;
    }

    public void setENGLISHNAME(String ENGLISHNAME) {
        this.ENGLISHNAME = ENGLISHNAME;
    }

    //必须要重写，不然绑定spinner会有问题
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Name;
    }

}
