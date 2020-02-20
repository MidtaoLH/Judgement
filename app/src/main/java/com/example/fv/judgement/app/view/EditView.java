package com.example.fv.judgement.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fv.judgement.R;

public class EditView extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_view);
        Intent i = getIntent();
        String type = i.getStringExtra("type");
        String code = i.getStringExtra("code");
        initview(type,code);
    }
    protected void initview(String type,String code)
    {
        if(type=="0")//请假新增
        {
            TextView lblTitle=(TextView)findViewById(R.id.lbltype);
            String str=" <font color='#FF0000'>*</font> 请假类型";
            lblTitle.setText(str);
            TextView lblbalance=(TextView)findViewById(R.id.lblbalance);
            str="假期余额";
            lblbalance.setText(str);
            TextView lblstartdate=(TextView)findViewById(R.id.lblstartdate);
            str=" <font color='#FF0000'>*</font> 开始时间";
            lblstartdate.setText(str);
            TextView lblenddate=(TextView)findViewById(R.id.lblenddate);
            str=" <font color='#FF0000'>*</font> 结束时间";
            lblenddate.setText(str);
            TextView lblCause=(TextView)findViewById(R.id.lblCause);
            str=" <font color='#FF0000'>*</font> 请假事由";
            lblCause.setText(str);
            EditText txtcause=(EditText)findViewById(R.id.txtcause);
            str="请输入请假事由";
            txtcause.setText(str);
            Button btnpath = (Button) findViewById(R.id.btnpath);
            btnpath.setOnClickListener(this);
            Button btnsave = (Button) findViewById(R.id.btnsave);
            btnsave.setOnClickListener(this);
            Button btnsubmit = (Button) findViewById(R.id.btnsubmit);
            btnsubmit.setOnClickListener(this);
        }
        else if(type=="1")//请假编辑
        {
            TextView lblTitle=(TextView)findViewById(R.id.lbltype);
            String str=" <font color='#FF0000'>*</font> 请假类型";
            lblTitle.setText(str);
            TextView lblbalance=(TextView)findViewById(R.id.lblbalance);
            str="假期余额";
            lblbalance.setText(str);
            TextView lblstartdate=(TextView)findViewById(R.id.lblstartdate);
            str=" <font color='#FF0000'>*</font> 开始时间";
            lblstartdate.setText(str);
            TextView lblenddate=(TextView)findViewById(R.id.lblenddate);
            str=" <font color='#FF0000'>*</font> 结束时间";
            lblenddate.setText(str);
            TextView lblCause=(TextView)findViewById(R.id.lblCause);
            str=" <font color='#FF0000'>*</font> 请假事由";
            lblCause.setText(str);
            EditText txtcause=(EditText)findViewById(R.id.txtcause);
            str="请输入请假事由";
            txtcause.setText(str);
            Button btnpath = (Button) findViewById(R.id.btnpath);
            btnpath.setOnClickListener(this);
            Button btnsave = (Button) findViewById(R.id.btnsave);
            btnsave.setOnClickListener(this);
            Button btnsubmit = (Button) findViewById(R.id.btnsubmit);
            btnsubmit.setOnClickListener(this);

            TextView typetag=(TextView)findViewById(R.id.typetag);
            str=" <font color='#FF0000'>*</font> 开始时间";
            typetag.setText(str);

        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnpath:
                break;
            case R.id.btnsave:
                break;
            case R.id.btnsubmit:
                break;
            default:
                break;
        }
    }
}