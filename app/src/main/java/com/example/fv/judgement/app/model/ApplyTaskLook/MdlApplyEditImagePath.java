package com.example.fv.judgement.app.model.ApplyTaskLook;

import zuo.biao.library.base.BaseModel;

public class MdlApplyEditImagePath extends BaseModel
{
    private String AnnexPath;

    /**默认构造方法，JSON等解析时必须要有
     */
    public MdlApplyEditImagePath() {
        //default
    }
    public MdlApplyEditImagePath(long id) {
        this();
        this.id = id;
    }
    public MdlApplyEditImagePath(long id, String name) {
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

    public String getAnnexPath() {
        return AnnexPath;
    }

    public void setAnnexPath(String annexPath) {
        AnnexPath = annexPath;
    }
}

