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
import com.example.fv.judgement.app.model.AgentListModel;

import zuo.biao.library.base.BaseView;
import zuo.biao.library.util.CommonUtil;

public class AgentListView extends BaseView<AgentListModel> implements View.OnClickListener
{
    private static final String TAG = "AgentListView";

    //构造需继承样式继承列表样式R.layout.user_view
    public AgentListView(Activity context, ViewGroup parent) {
        super(context, R.layout.examine_viewlistnew, parent);
    }
    public ImageView ivUserViewHead; //创建画面所需控件
    public TextView tvCaseName;
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
        ivUserViewHead = findView(R.id.ivUserViewHead, this);
        tvCaseName = findView(R.id.tvCaseName);
        tvCaseType = findView(R.id.tvCaseType);
        tvBeginDate = findView(R.id.tvBeginDate);
        tvEndDate = findView(R.id.tvEndDate);
        tvStatus = findView(R.id.tvStatus);
        tvCaseDate = findView(R.id.tvCaseDate);

        return super.createView();
    }
    @Override
    public void bindView(AgentListModel data_) {
        super.bindView(data_ != null ? data_ : new AgentListModel());

        if(data_.getDocumentTypeNM().length() <= 0)
        {

        }
        else
        {
            //格式化头像地址
            String strPhoto = String.format(GlobalVariableApplication.SERVICE_PHOTO_URL, data_.getApplyManPhoto());
            Glide.with(context).asBitmap().load(strPhoto).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth() / 2));
                }
            });
            tvCaseName.setText(data_.getDocumentTypeNM());
            tvCaseType.setText("");
            tvBeginDate.setText("开始时间:" + data_.getAgentStartDate());
            tvEndDate.setText("结束时间:" + data_.getAgentEndDate());
            tvCaseDate.setText("");
            tvStatus.setText(data_.getAgentStatusNM());
            tvCaseType.setVisibility(View.VISIBLE);
            tvCaseDate.setVisibility(View.VISIBLE);
            tvStatus.setTextColor(GlobalVariableApplication.cellStatusColor);
        }


    }
    @Override
    public void onClick(View v) {

    }
}
