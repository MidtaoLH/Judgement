package com.example.fv.judgement.app.model;

import zuo.biao.library.base.BaseModel;

public class MdlExamineEditImagePath extends BaseModel
{
    private String ImagePath;

    /**默认构造方法，JSON等解析时必须要有
     */
    public MdlExamineEditImagePath() {
        //default
    }
    public MdlExamineEditImagePath(long id) {
        this();
        this.id = id;
    }
    public MdlExamineEditImagePath(long id, String name) {
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

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}

