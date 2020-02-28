package com.example.fv.judgement.app.activity.Notice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.Home.PageHome;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.manager.CacheManager;
import zuo.biao.library.ui.BottomMenuView;
import zuo.biao.library.ui.BottomMenuWindow;
import zuo.biao.library.ui.EditTextInfoActivity;
import zuo.biao.library.ui.TextClearSuit;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

public class NoticeDetail extends AppCompatActivity {

    private ImageButton returnbtn;
    private TextView tvtitle;
    private TextView tvgroupname;
    private TextView tvdate;
    private TextView tvcontent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Intent i = getIntent();

        //获取数据包,然后获取数据包中的参数
        Bundle B = i.getExtras();

        String title = B.getString("title");
        String groupname = B.getString("groupname");
        String date = "发布时间：" +  B.getString("date");
        String content = B.getString("content");


        returnbtn = findViewById(R.id.returnbtn);
        tvtitle = findViewById(R.id.tvtitle);
        tvgroupname = findViewById(R.id.tvgroupname);
        tvdate = findViewById(R.id.tvdate);
        tvcontent = findViewById(R.id.tvcontent);

        tvtitle.setText(title);
        tvgroupname.setText(groupname);
        tvdate.setText(date);
        tvcontent.setText(content);

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

    }


}


