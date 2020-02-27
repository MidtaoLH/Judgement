package com.example.fv.judgement.app.activity.AttendanceCalendar;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.LoginUserModel;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.CommonUtil;

public class AttendanceCalendar  extends BaseActivity implements View.OnClickListener {
    private ImageView ivUserViewHead;
    private TextView tvEmpName,tvDept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_calendar);
        initView();
        initData();
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void initView() {
        ivUserViewHead=(ImageView) findViewById(R.id.ivUserViewHead);
        tvEmpName=(TextView) findViewById(R.id.tvEmpName);
        tvDept=(TextView) findViewById(R.id.tvDept);
    }

    @Override
    public void initData() {
        LoginUserModel model= GlobalInformationApplication.getInstance().getCurrentUser();
        String url=String.format(GlobalVariableApplication.SERVICE_PHOTO_URL,model.getCode());
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
            }
        });
        tvEmpName.setText(model.getName());
        tvDept.setText(model.getGroupName());
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {

    }
}
