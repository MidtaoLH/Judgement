package com.example.fv.judgement.app.activity.WayCheck;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.fv.judgement.app.activity.Home.PageHome;
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
import zuo.biao.library.ui.PlacePickerWindow;
import zuo.biao.library.util.StringUtil;

import static android.app.Activity.RESULT_OK;
import static com.example.fv.judgement.app.activity.WayCheck.EmpPickerWindow.RESULT_PLACE_LIST;
import static com.example.fv.judgement.app.application.GlobalVariableApplication.IMAGE_URL;


public class WayCheck_view  extends BaseView<WayCheckModel> implements View.OnClickListener {


    private static final String TAG = "DemoView";

    public  Activity context2;

    public WayCheck_view(Activity context, ViewGroup parent) {
        super(context, R.layout.way_check_view, parent);  //TODO demo_view改为你所需要的layout文件

        this.context2 = context;
    }

    //示例代码<<<<<<<<<<<<<<<<
    public ImageView ivUserViewHead; //头像
    public TextView tvname;         //用户名
    public TextView tvlevelname;    //级别
    public TextView tvgroupname;    //部门名称

    public Button btnadd;
    public Button btndelete;
    //示例代码>>>>>>>>>>>>>>>>
    @Override
    public View createView() {

        //示例代码<<<<<<<<<<<<<<<<
        ivUserViewHead = findView(R.id.ivUserViewHead, this);
        tvname = findView(R.id.tvname);
        tvlevelname = findView(R.id.tvlevelname);
        btnadd = findView(R.id.btnadd);
        btndelete= findView(R.id.btndelete);
        tvgroupname= findView(R.id.tvgroupname);

        btnadd.setOnClickListener(this);
        btndelete.setOnClickListener(this);
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

        if(data.getEditflag().equals("1"))
        {
            btndelete.setVisibility(View.VISIBLE);
        }
        else
        {
            btndelete.setVisibility(View.INVISIBLE);
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

        switch (v.getId()) {
            case R.id.btnadd:

                Bundle B = new Bundle();
                B.putString("index",data.getIndex());
                B.putString("level",data.getLevelname());

                toActivity(EmpPickerWindow.createIntent(context, "", 2,data.getIndex(),data.getLevelname()), 32, false);
                //toActivity(PlacePickerWindow.createIntent(context, "", 2), 32, false);
                //toActivity(new Intent(context, WayChose.class).putExtras(B));//一般用id，这里position仅用于测试 id));//

                break;
            case R.id.btndelete:
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("")
                        .setMessage("test")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                WayCheckFragmentActivity activity = (WayCheckFragmentActivity) context;

                                if(activity.demoFragment.list.size() > 0)
                                {
                                    activity.demoFragment.list.remove( Integer.parseInt(data.getIndex() ));
                                    activity.demoFragment.setList( activity.demoFragment.list);
                                }


                            }


                        }).create().show();
            default:
                break;
        }

    }
    //示例代码>>>>>>>>>>>>>>>>



}

