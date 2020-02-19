package com.example.fv.judgement.app.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.model.ExamineModel;

import zuo.biao.library.base.BaseView;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.StringUtil;

public class ExamineListView extends BaseView<ExamineModel> implements View.OnClickListener
{
    //构造需继承样式继承列表样式R.layout.user_view
    public ExamineListView(Activity context, ViewGroup parent) {
        super(context, R.layout.examine_view, parent);
    }
    public ImageView ivUserViewHead; //创建画面所需控件
    public ImageView ivUserViewStar;
    public TextView tvUserViewSex;
    public TextView tvUserViewName;
    public TextView tvUserViewId;
    public TextView tvUserViewNumber;

     //创建画面控件关联
    public View createView() {
        ivUserViewHead = findView(R.id.ivUserViewHead, this);
        tvUserViewSex = findView(R.id.tvUserViewSex, this);
        tvUserViewName = findView(R.id.tvUserViewName);
        tvUserViewId = findView(R.id.tvUserViewId);
        tvUserViewNumber = findView(R.id.tvUserViewNumber);

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
        tvUserViewSex.setBackgroundResource(data.getSex() == ExamineModel.SEX_FEMALE
                ? R.drawable.circle_pink : R.drawable.circle_blue);
        tvUserViewSex.setText(data.getSex() == ExamineModel.SEX_FEMALE ?  "女" : "男");
        tvUserViewSex.setTextColor(getColor(data.getSex() == ExamineModel.SEX_FEMALE ? R.color.pink : R.color.blue));

        tvUserViewName.setText(StringUtil.getTrimedString(data.getName()));
        tvUserViewId.setText("ID:" + data.getId());
        tvUserViewNumber.setText("Phone:" + StringUtil.getNoBlankString(data.getPhone()));
    }
    public void onClick(View v) {
    }

}
