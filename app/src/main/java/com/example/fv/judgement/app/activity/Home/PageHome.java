package com.example.fv.judgement.app.activity.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripTabbar;
import com.example.fv.judgement.app.activity.Leave.LeaveTabActivity;
import com.example.fv.judgement.app.activity.MyExamineList.MainTabActivity;
import com.example.fv.judgement.app.activity.Notice.MainNoticeActivity;

public class PageHome extends AppCompatActivity {
    private Button myApplybtn;
    private Button waitmyApplybtn;
    private Button waitmyReadbtn;
    private Button leavebtn;
    private Button gooutbtn;
    private Button businessTripbtn;
    private Button noticebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_home);

        myApplybtn = findViewById(R.id.myApply);
        waitmyApplybtn = findViewById(R.id.waitmyApply);
        waitmyReadbtn = findViewById(R.id.waitmyRead);
        leavebtn = findViewById(R.id.leave);
        gooutbtn = findViewById(R.id.goout);
        businessTripbtn = findViewById(R.id.businessTrip);
        noticebtn = findViewById(R.id.notice);

        waitmyApplybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PageHome.this, MainTabActivity.class);
                startActivity(intent);
            }
        });
        leavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PageHome.this, LeaveTabActivity.class);
                startActivity(intent);
            }
        });
        businessTripbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PageHome.this, BusinessTripTabbar.class);
                startActivity(intent);
            }
        });
        noticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PageHome.this, MainNoticeActivity.class);
                startActivity(intent);
            }
        });
    }
}
