package com.example.fv.judgement.app.model.ApplyTaskLook;

import zuo.biao.library.base.BaseModel;

public class ApplyTaskLookImage extends BaseModel
{
    private static final long serialVersionUID = 1L;

    /**默认构造方法，JSON等解析时必须要有
     */

    public ApplyTaskLookImage() {
        //default
    }
    public ApplyTaskLookImage(long id) {
        this();
        this.id = id;
    }
    public ApplyTaskLookImage(long id, String name) {
        this(id);
    }
    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }
//[{"ProcessInsAttachID":211,"AwardID_FK":49,"AttachFileName":"1","AttachFilePath":"APP/Annex/20200164CC216/1.png"},
//    {"ProcessInsAttachID":210,"AwardID_FK":49,"AttachFileName":"2","AttachFilePath":"APP/Annex/20200164CC216/2.png"},
//    {"ProcessInsAttachID":209,"AwardID_FK":49,"AttachFileName":"3","AttachFilePath":"APP/Annex/20200164CC216/3.png"},
//    {"ProcessInsAttachID":208,"AwardID_FK":49,"AttachFileName":"4","AttachFilePath":"APP/Annex/20200164CC216/4.png"},
//    {"ProcessInsAttachID":207,"AwardID_FK":49,"AttachFileName":"5","AttachFilePath":"APP/Annex/20200164CC216/5.png"}]

    private String AttachFilePath;


    public String getAttachFilePath() {
        return AttachFilePath;
    }

    public void setAttachFilePath(String attachFilePath) {
        AttachFilePath = attachFilePath;
    }
}

