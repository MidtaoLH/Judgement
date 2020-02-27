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
        super(context, R.layout.activity_notice_list_view, parent);
    }

    public TextView tvnoticetitle; //公告的头部名称
    public TextView tvCaseDate;  //公告日期
    public TextView tvNoticecontent; //公告内容


    //  public TextView tvYuLan;
    @SuppressLint("InflateParams")
    @Override
    //创建画面控件关联
    public View createView() {
        //ivUserViewHead = findView(R.id.ivUserViewHead, this);
        tvnoticetitle = findView(R.id.tvnoticetitle);


        tvCaseDate = findView(R.id.tvCaseDate);
        tvNoticecontent= findView(R.id.tvNoticecontent);

        return super.createView();
    }
    @Override
    public void bindView(NoticeModel data_){
        super.bindView(data_ != null ? data_ : new NoticeModel());

        String aa =  data_.getNewsTheme().toString();
        String bb = data_.getNewsDate().toString();
        String cc = data_.getNewsContent().toString();

        tvnoticetitle.setText(aa);
        tvCaseDate.setText(bb);
        tvNoticecontent.setText(cc);
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
