package com.example.fv.judgement.app.view;

import android.annotation.SuppressLint;
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
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.BusinessTripListModel;

import zuo.biao.library.base.BaseView;
import zuo.biao.library.util.CommonUtil;

public class BusinessTripListView extends BaseView<BusinessTripListModel> implements View.OnClickListener
{
    private static final String TAG = "BusinessTripListView";

    //构造需继承样式继承列表样式R.layout.user_view
    public BusinessTripListView(Activity context, ViewGroup parent) {
        super(context, R.layout.examine_viewlistnew, parent);
    }
    public ImageView ivUserViewHead; //创建画面所需控件
    public TextView tvCaseName;
    public TextView tvBeginDate;
    public TextView tvEndDate;
    public TextView tvStatus;
    public TextView tvCaseDate;
    public TextView tvCaseType;
    //  public TextView tvYuLan;
    @SuppressLint("InflateParams")
    @Override
    //创建画面控件关联
    public View createView() {
        ivUserViewHead = findView(R.id.ivUserViewHead, this);
        tvCaseName = findView(R.id.tvCaseName);
        tvBeginDate = findView(R.id.tvBeginDate);
        tvEndDate = findView(R.id.tvEndDate);
        tvStatus = findView(R.id.tvStatus);
        tvCaseDate= findView(R.id.tvCaseDate);
        tvCaseType = findView(R.id.tvCaseType);

        return super.createView();
    }
    @Override
    public void bindView(BusinessTripListModel data_) {
        super.bindView(data_ != null ? data_ : new BusinessTripListModel());

        //格式化头像地址
        String strPhoto = String.format(GlobalVariableApplication.SERVICE_PHOTO_URL, data_.getApplyManPhoto());
        Glide.with(context).asBitmap().load(strPhoto).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth() / 2));
            }
        });
        tvCaseName.setText(data_.getCaseName());
        tvBeginDate.setText("开始时间:" + data_.getBeignDate());
        tvEndDate.setText("结束时间:" + data_.getEndDate());
        tvCaseDate.setText(data_.getCaseDate());
        tvStatus.setText(data_.getCaseStatusTxt());
        tvStatus.setTextColor(GlobalVariableApplication.cellStatusColor);
        tvCaseType.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {

    }

}
