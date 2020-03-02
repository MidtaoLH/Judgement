package com.example.fv.judgement.app.activity.Agent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.asm.Label;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.Leave.LeaveEdit;
import com.example.fv.judgement.app.activity.Login.MainLogin;
import com.example.fv.judgement.app.activity.WayCheck.EmpPickerWindow;
import com.example.fv.judgement.app.adapter.GridImageAdapter;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.AgentInfoModel;
import com.example.fv.judgement.app.model.AgentModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.WayCheckModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DebugUtil;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.DatePickerWindow;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.StringUtil;
import zuo.biao.library.util.TimeUtil;

public class AgentShow extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = LeaveEdit.class.getSimpleName();
    private String  agentid, userID, groupid, empID, processid, iosid
            , empname, vatcationid,ApplyCode,userHour,processApplyCode,proCelReson,vtype;

    private  String agentnameid,agentname,timestart,timeend,agentgroupnameid,agentgroupname;

    private TextView tvname,tvgroupname,tvdate,tvcreattime,tvusetime;

    private Button btnstop;
    private TextView lbUserViewHead;
    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private int maxSelectNum = GlobalVariableApplication.maxImageSelectNum;
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();

    private static final int REQUEST_TO_DATE_PICKER = 33;
    private static final int REQUEST_TO_DATE_PICKEREND = 34;
    private int[] selectedDate = new int[]{1971, 0, 1};
    private int[] selectedStaraDate,selectedEndDate;
    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private Intent intent;
    public static Intent createIntent(Context context, String agentnameid, String agentname
            , String timestart, String timeend, String agentid , String agentgroupnameid, String agentgroupname) {
        return new Intent(context, AgentShow.class) .
                putExtra("agentnameid", agentnameid).
                putExtra("agentname", agentname).
                putExtra("timestart", timestart).
                putExtra("timeend", timeend).
                putExtra("agentid", agentid).
                putExtra("agentgroupnameid", agentgroupnameid).
                putExtra("agentgroupname", agentgroupname)
                ;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_show);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        agentid = StringUtil.get(getIntent().getStringExtra("agentid"));
        if(agentid.length() > 0)
        {

            initData();
        }
        else
        {

        }
        initEvent();


    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void initView() {
        LoginUserModel model = GlobalInformationApplication.getInstance().getCurrentUser();
        userID = model.getId();
        empID = model.getEmpID();
        groupid = model.getGroupid();
        empname = model.getGroupName();
        iosid =model.getAdId();
        userHour = model.getUserHour();

        intent = getIntent();
        tvname = (TextView) findViewById(R.id.tvname);
        tvgroupname = (TextView) findViewById(R.id.tvgroupname);
        btnstop= (Button) findViewById(R.id.btnstop);


        lbUserViewHead = (TextView)findViewById(R.id.lbUserViewHead);

        tvdate = (TextView) findViewById(R.id.tvdate);
        tvcreattime = (TextView) findViewById(R.id.tvcreattime);
        tvusetime = (TextView) findViewById(R.id.tvusetime);


        btnstop.setOnClickListener(this);


    }
    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void initData() {
        //必须在onCreate方法内调用
        //可编辑状态的赋值

        String datastring =GetInfo();

        if (datastring.equals(GlobalVariableApplication.UnLoginFlag))
        {
            showShortToast(GlobalVariableApplication.UnLoginMessage);
            Intent intent = new Intent();
            intent.setClass(this.context, MainLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
        {
            List<AgentInfoModel> AM=new ArrayList<AgentInfoModel>();
            //json转为实体
            Type type1=new TypeToken<List<AgentInfoModel>>(){}.getType();
            AM = new Gson().fromJson(datastring, type1);

            if(AM.size() > 0)
            {
                tvname.setText(AM.get(0).getEmpName());
                tvgroupname.setText(AM.get(0).getDeptName());

                tvdate.setText(AM.get(0).getAgentDate());
                tvcreattime.setText(AM.get(0).getCreateDate());

                if(AM.get(0).getAppDate().length() > 0)
                {
                    tvusetime.setText(AM.get(0).getAppDate());
                }

                lbUserViewHead.setText(AM.get(0).getAgentStatus());


                if(AM.get(0).getAgentStatus().contains("终止"))
                {

                    btnstop.setVisibility(View.INVISIBLE);
                }
                else
                {
                    btnstop.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    public String GetInfo() {
        String datastring = "";
        try {

            String methodName = "GetAgentSet";
            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);
            soapObject.addProperty("userID",userID );
            soapObject.addProperty("agentID", agentid);
            soapObject.addProperty("iosid", iosid);
            HttpRequest http = new HttpRequest();
            datastring = http.httpWebService_GetString(methodName, soapObject);
            return datastring;
        } catch (Exception e) {
            MyLog.writeLogtoFile("错误", "AgentEdit", "GetInfo", e.toString(), "0");
        }
        return "";
    }
    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void initEvent() {//必须在onCreate方法内调用


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                finish();
                break;
            case  R.id.btnstop:

                String deleteflag = deleteinfo();

                if (deleteflag.equals(GlobalVariableApplication.UnLoginFlag))
                {
                    showShortToast(GlobalVariableApplication.UnLoginMessage);
                    Intent intent = new Intent();
                    intent.setClass(this.context, MainLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    if(deleteflag.contains("成功"))
                    {
                        new AlertDialog.Builder(AgentShow.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("")
                                .setMessage("终止成功！")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //更新成功
                                        finish();
                                    }


                                }).create().show();

                    }
                    else
                    {
                        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
                        new AlertDialog.Builder(AgentShow.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("")
                                .setMessage(deleteflag.replaceAll(regEx,""))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }


                                }).create().show();
                    }
                }
                break;
        }
    }

    public String deleteinfo() {
        String datastring = "";
        try {

            if(agentid.length() > 0)
            {
                String methodName = "AgentSetEND";
                SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                        methodName);
                soapObject.addProperty("userID",userID );
                soapObject.addProperty("agentSetID",agentid );
                soapObject.addProperty("agentEmpID", agentnameid);
                soapObject.addProperty("iosid", iosid);

                HttpRequest http = new HttpRequest();
                datastring = http.httpWebService_GetString(methodName, soapObject);
                return datastring;
            }


        } catch (Exception e) {
            MyLog.writeLogtoFile("错误", "AgentEdit", "GetInfo", e.toString(), "0");
        }
        return "";
    }



    @Override
    public boolean onLongClick(View v)
    {

        return false;
    }
}
