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
import com.example.fv.judgement.app.model.ExamineModel;
import android.view.View.OnClickListener;

import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.StringUtil;

public class ExamineListView extends BaseView<ExamineModel> implements OnClickListener
{
    private static final String TAG = "UserView";

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
    @SuppressLint("InflateParams")
    @Override
     //创建画面控件关联
    public View createView() {
        ivUserViewHead = findView(R.id.ivUserViewHead, this);
        tvUserViewSex = findView(R.id.tvUserViewSex, this);
        tvUserViewName = findView(R.id.tvUserViewName);
        tvUserViewId = findView(R.id.tvUserViewId);
        tvUserViewNumber = findView(R.id.tvUserViewNumber);

        return super.createView();
    }
    @Override
    public void bindView(ExamineModel data_){
        super.bindView(data_ != null ? data_ : new ExamineModel());

        Glide.with(context).asBitmap().load(data.getDocumentName()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
            }
        });

        tvUserViewName.setText(StringUtil.getTrimedString(data.getDocumentName()));
        tvUserViewId.setText("ID:" + data.getId());
        tvUserViewNumber.setText("Phone:" + StringUtil.getNoBlankString(data.getDocumentName()));
    }
    @Override
    public void onClick(View v) {
        if (BaseModel.isCorrect(data) == false) {
            return;
        }
        switch (v.getId()) {
            case R.id.ivUserViewHead:
                toActivity(WebViewActivity.createIntent(context, data.getDocumentName(), data.getDocumentName()));
                break;
            default:
                switch (v.getId()) {

                }
                bindView(data);
                break;
        }
    }


}
