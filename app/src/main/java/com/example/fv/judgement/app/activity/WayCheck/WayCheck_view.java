package com.example.fv.judgement.app.activity.WayCheck;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.GoOut.GoOut;
import com.example.fv.judgement.app.activity.Login.MainLogin;
import com.example.fv.judgement.app.application.GlobalMethodApplication;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.WayCheckModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseView;
import zuo.biao.library.model.Entry;
import zuo.biao.library.util.StringUtil;

import static com.example.fv.judgement.app.application.GlobalVariableApplication.IMAGE_URL;


public class WayCheck_view  extends BaseView<WayCheckModel> implements View.OnClickListener {


    private static final String TAG = "DemoView";

    public WayCheck_view(Activity context, ViewGroup parent) {
        super(context, R.layout.way_check_view, parent);  //TODO demo_view改为你所需要的layout文件
    }


    //示例代码<<<<<<<<<<<<<<<<
    public ImageView ivUserViewHead; //头像
    public TextView tvname;         //用户名
    public TextView tvlevelname;    //级别
    public TextView tvgroupname;    //部门名称

    public Button btnadd;

    //示例代码>>>>>>>>>>>>>>>>
    @Override
    public View createView() {

        //示例代码<<<<<<<<<<<<<<<<
        ivUserViewHead = findView(R.id.ivUserViewHead, this);
        tvname = findView(R.id.tvname);
        tvlevelname = findView(R.id.tvlevelname);
        btnadd = findView(R.id.btnadd);
        tvgroupname= findView(R.id.tvgroupname);

        btnadd.setOnClickListener(this);

        return super.createView();
    }


    @Override
    public void bindView(WayCheckModel data_){
        //示例代码<<<<<<<<<<<<<<<<
        super.bindView(data_);
        //itemView.setBackgroundResource(selected ? R.drawable.alpha3 : R.drawable.white_to_alpha);

        if(data.getEnglishname().toString().equals("button"))
        {
            btnadd.setVisibility(View.VISIBLE);
            ivUserViewHead.setVisibility(View.INVISIBLE); //头像
            tvname.setVisibility(View.INVISIBLE);         //用户名
            tvlevelname.setVisibility(View.INVISIBLE);    //级别
            tvgroupname.setVisibility(View.INVISIBLE);    //部门名称


        }
        else if(data.getEnglishname().toString().equals("null"))
        {
            btnadd.setVisibility(View.INVISIBLE);
            ivUserViewHead.setVisibility(View.INVISIBLE); //头像
            tvname.setVisibility(View.INVISIBLE);         //用户名
            tvlevelname.setVisibility(View.INVISIBLE);    //级别
            tvgroupname.setVisibility(View.INVISIBLE);    //部门名称
        }
        else
        {
            URL url = null;
            try {
                //data.getEnglishname()
                url = new URL(IMAGE_URL + "moren" + ".png");
                ivUserViewHead.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();

            }

            btnadd.setVisibility(View.INVISIBLE);
            ivUserViewHead.setVisibility(View.VISIBLE); //头像
            tvname.setVisibility(View.VISIBLE);         //用户名
            tvlevelname.setVisibility(View.VISIBLE);    //级别
            tvgroupname.setVisibility(View.VISIBLE);    //部门名称
        }

        tvname.setText(StringUtil.getTrimedString(data.getName()));
        tvlevelname.setText(StringUtil.getTrimedString(data.getLevelname()));
        tvgroupname.setText(StringUtil.getTrimedString(data.getGroupname()));
    }


    //示例代码<<<<<<<<<<<<<<<<
    @Override
    public void onClick(View v) {
        if (data == null) {
            return;
        }

    }
    //示例代码>>>>>>>>>>>>>>>>



}

