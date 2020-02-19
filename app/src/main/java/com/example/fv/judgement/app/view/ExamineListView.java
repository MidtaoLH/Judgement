package com.example.fv.judgement.app.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.model.ExamineModel;

import zuo.biao.library.base.BaseView;
import zuo.biao.library.util.CommonUtil;

public class ExamineListView extends BaseView<ExamineModel>
{
    //构造需继承样式继承列表样式R.layout.user_view
    public ExamineListView(Activity context, ViewGroup parent) {
        super(context, R.layout.activity_leave_edit, parent);
    }
    public ImageView ivUserViewHead; //创建画面所需控件

     //   创建画面控件关联
    public View createView() {
       // ivUserViewHead = findView(R.id.ivUserViewHead, this);
        return super.createView();
    }
    public void bindView(ExamineModel data_){
        super.bindView(data_ != null ? data_ : new ExamineModel());

        Glide.with(context).asBitmap().load(data.getHead()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
            }
        });
 
    }
    public void onClick(View v) {
    }

}
