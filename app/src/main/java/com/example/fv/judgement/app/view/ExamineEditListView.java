package com.example.fv.judgement.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.MdlExamineEditDetail;

import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.CommonUtil;

public class ExamineEditListView extends BaseView<MdlExamineEditDetail> implements OnClickListener
{
    private static final String TAG = "ExamineEditListView";

    //构造需继承样式继承列表样式R.layout.user_view
    public ExamineEditListView(Activity context, ViewGroup parent) {
        super(context, R.layout.examine_edit_list, parent);
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

        tvCaseDate = findView(R.id.tvRemark);
        return super.createView();
    }
    @Override
    public void bindView(MdlExamineEditDetail data_){
        super.bindView(data_ != null ? data_ : new MdlExamineEditDetail());

        tvCaseDate.setText(data_.getName());

    }
    @Override
    public void onClick(View v) {
        if (BaseModel.isCorrect(data) == false) {
            return;
        }
        switch (v.getId()) {
            case R.id.ivUserViewHead:
             //   toActivity(WebViewActivity.createIntent(context, data.getDocumentName(), data.getDocumentName()));
                break;
            default:
                switch (v.getId()) {

                }
                bindView(data);
                break;
        }
    }

}
