package com.example.fv.judgement.app.activity.Leave;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.util.HttpRequest;

import org.ksoap2.serialization.SoapObject;

import java.util.List;

public class LeaveEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_edit);
        diaoyong();
    }
    public  void diaoyong()
    {
        String methodName = "CheckUser";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                methodName);
        soapObject.addProperty("User", "zhaodan");
        soapObject.addProperty("Password", "111111");
        soapObject.addProperty("macid", "dddd");
        HttpRequest http=new HttpRequest();
        List<LoginUserModel> list=http.httpWebService(methodName,soapObject);

    }
}
