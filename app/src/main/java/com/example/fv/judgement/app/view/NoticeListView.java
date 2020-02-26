package com.example.fv.judgement.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.NoticeModel;

import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.CommonUtil;

public class NoticeListView extends BaseView<NoticeModel> implements View.OnClickListener
{
    private static final String TAG = "NoticeListView";

    //构造需继承样式继承列表样式R.layout.user_view
    public NoticeListView(Activity context, ViewGroup parent) {
        super(context, R.layout.examine_viewlistnew, parent);
    }

    public TextView tvnoticetitle; //公告的头部名称
    public TextView tvCaseType;
    public TextView tvBeginDate;
    public TextView tvEndDate;
    public TextView tvStatus;
    public TextView tvCaseDate;
    //  public TextView tvYuLan;
    @SuppressLint("InflateParams")
    @Override
    //创建画面控件关联
    public View createView() {
        //ivUserViewHead = findView(R.id.ivUserViewHead, this);
        tvnoticetitle = findView(R.id.tvnoticetitle);
        tvCaseType = findView(R.id.tvCaseType);
        tvBeginDate = findView(R.id.tvBeginDate);
        tvEndDate = findView(R.id.tvEndDate);
        tvStatus = findView(R.id.tvStatus);
        tvCaseDate = findView(R.id.tvCaseDate);
        return super.createView();
    }
    @Override
    public void bindView(NoticeModel data_){
        super.bindView(data_ != null ? data_ : new NoticeModel());

        //tvnoticetitle



        //格式化头像地址
//        String strPhoto = String.format(GlobalVariableApplication.SERVICE_PHOTO_URL,data_.getApplyManPhoto());
//        Glide.with(context).asBitmap().load(strPhoto).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
//            }
//        });
//        tvCaseName.setText(data_.getCaseName());
//        tvCaseType.setText(data_.getCaseTypeTxt());
//        tvBeginDate.setText("开始时间:" + data_.getBeginDate());
//        tvEndDate.setText("结束时间:" + data_.getEndDate());
//        tvCaseDate.setText(data_.getCaseDate());
//        tvStatus.setText(data_.getCaseStatusTxt());

        //特殊处理
        //String strType = data_.getDocumentName();
//        if (!strType.equals("请假")) {
//            tvCaseType.setVisibility(View.GONE);
//        }
//        else
//        {
//            tvCaseType.setVisibility(View.VISIBLE);
//        }
//        tvStatus.setTextColor(Color.rgb(0, 204, 204));
    }
    @Override
    public void onClick(View v) {
        if (BaseModel.isCorrect(data) == false) {
           return;
        }
        switch (v.getId()) {
            case R.id.ivUserViewHead:
                //toActivity(WebViewActivity.createIntent(context, data.getDocumentName(), data.getDocumentName()));
                break;
            default:
                switch (v.getId()) {

                }
                bindView(data);
                break;
        }
    }
}
