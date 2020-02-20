package com.example.fv.judgement.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.LeaveModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EditView extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_view);
        Intent i = getIntent();
        String type = i.getStringExtra("type");
        initview(type);
    }
    protected void initview(String type)
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
            txtcause.setHint(str);
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
            Button btnpath = (Button) findViewById(R.id.btnpath);
            btnpath.setOnClickListener(this);
            Button btnsave = (Button) findViewById(R.id.btnsave);
            btnsave.setOnClickListener(this);
            Button btnsubmit = (Button) findViewById(R.id.btnsubmit);
            btnsubmit.setOnClickListener(this);

            String json=GetInfo();
            //json转为实体
            Type LeaveModelType=new TypeToken<List<LeaveModel>>(){}.getType();
            List<LeaveModel> LU = new Gson().fromJson(json, LeaveModelType);

            TextView typetag=(TextView)findViewById(R.id.typetag);
            str= LU.get(0).getVatcationtype().toString();
            typetag.setText(str);

            TextView startdatetag=(TextView)findViewById(R.id.startdatetag);
            str= LU.get(0).getTimestart().toString();
            startdatetag.setText(str);

            TextView endDateTag=(TextView)findViewById(R.id.endDateTag);
            str= LU.get(0).getTimesend().toString();
            endDateTag.setText(str);

            TextView txtduration=(TextView)findViewById(R.id.txtduration);
            str= LU.get(0).getVatcationreason().toString();
            txtduration.setText(str);

            EditText txtcause=(EditText)findViewById(R.id.txtcause);
            str= LU.get(0).getVatcationreason().toString();
            txtcause.setText(str);

        }
    }
    public  String  GetInfo()
    {
        String datastring = "";
        try{
            //NSString *strURL = [NSString stringWithFormat:@"http://47.94.85.101:8095/AppWebService.asmx/VatcationSearchByID?userID=%@&VatcationID=%@&processid=%@&iosid=%@", userID,vatcationid,processid,iosid];
            Intent i = getIntent();
            String userID = i.getStringExtra("userID");
            String vatcationID = i.getStringExtra("VatcationID");
            String processid = i.getStringExtra("processid");
            String iosid = i.getStringExtra("iosid");

            String methodName = "VatcationSearchByID";
            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);
            soapObject.addProperty("userID", userID);
            soapObject.addProperty("VatcationID", vatcationID);
            soapObject.addProperty("processid", processid);
            soapObject.addProperty("iosid", iosid);
            HttpRequest http=new HttpRequest();
            datastring =http.httpWebService_GetString(methodName,soapObject);
            return datastring;
        } catch (Exception e) {
            MyLog.writeLogtoFile("错误","MainLogin","UseWebservice",e.toString(),"0");
        }
        return "";
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