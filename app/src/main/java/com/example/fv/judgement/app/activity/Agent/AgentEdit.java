package com.example.fv.judgement.app.activity.Agent;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.Leave.LeaveEdit;
import com.example.fv.judgement.app.activity.Leave.LeaveType;
import com.example.fv.judgement.app.activity.Login.MainLogin;
import com.example.fv.judgement.app.activity.WayCheck.EmpPickerWindow;
import com.example.fv.judgement.app.adapter.GridImageAdapter;
import com.example.fv.judgement.app.application.FullyGridLayoutManager;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalMethodApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.AgentModel;
import com.example.fv.judgement.app.model.LeaveModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.WayCheckModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.DatePickerWindow;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.StringUtil;
import zuo.biao.library.util.TimeUtil;

public class AgentEdit extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = LeaveEdit.class.getSimpleName();
    private String  agentid, userID, groupid, empID, processid, iosid
            , empname, vatcationid,ApplyCode,userHour,processApplyCode,proCelReson,vtype;

    private  String agentnameid,agentname,timestart,timeend,agentgroupnameid,agentgroupname;

    private TextView tvname,tvgroupname,startdatetag,endDateTag;

    private Button  btnsave, btnsubmit;

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
        return new Intent(context, AgentEdit.class) .
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
        setContentView(R.layout.activity_agent_edit);

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
        btnsave= (Button) findViewById(R.id.btnsave);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);

        startdatetag = (TextView) findViewById(R.id.startdatetag);
        endDateTag = (TextView) findViewById(R.id.endDateTag);

        btnsave.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);

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
        else if(datastring.length()>0)  {

            List<AgentModel> AM=new ArrayList<AgentModel>();
            //json转为实体
            Type type1=new TypeToken<List<AgentModel>>(){}.getType();
            AM = new Gson().fromJson(datastring, type1);

            if(AM.size() > 0)
            {
                agentnameid = AM.get(0).getEmpID();
                agentname = AM.get(0).getEmpName();
                timestart = AM.get(0).getAgentStartDate();
                timeend = AM.get(0).getAgentEndDate();
                agentgroupnameid = AM.get(0).getDeptID();
                agentgroupname = AM.get(0).getDeptName();

                tvname.setText(agentname);
                tvgroupname.setText(agentgroupname);
                startdatetag.setText(timestart);
                endDateTag.setText(timeend);
            }
        }
    }



    public String SaveInfo() {
        try {

            if(tvname.getText().toString().trim().equals(""))
            {
                return "请输入代理人";
            }

            if(startdatetag.getText().toString().trim().equals(""))
            {
                return "请输入开始日期";
            }
            if(endDateTag.getText().toString().trim().equals(""))
            {
                return "请输入结束日期";
            }

            ApplyCode = "";
            String methodName = "btnsave_new";
            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);
            soapObject.addProperty("ProcessApplyCode", processApplyCode);

            soapObject.addProperty("name", empname);
            soapObject.addProperty("leavleid", vatcationid);
            soapObject.addProperty("processid", processid);
            soapObject.addProperty("imagecount", recyclerView.getItemDecorationCount());
            soapObject.addProperty("applycode", ApplyCode);
            soapObject.addProperty("CelReson", proCelReson);
            soapObject.addProperty("iosid", iosid);
            HttpRequest http = new HttpRequest();
            String datastring = http.httpWebService_GetString(methodName, soapObject);
        } catch (Exception e) {
            MyLog.writeLogtoFile("错误", "AgentEdit", "SaveInfo", e.toString(), "0");
        }
        return "";
    }
    public String GetInfo() {
        String datastring = "";
        try {

            String methodName = "GetAgentSetInfo";
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
        findView(R.id.leavetype).setOnClickListener(this);
        findView(R.id.llStartDateTag).setOnClickListener(this);
        findView(R.id.llEndDateTag).setOnClickListener(this);
        findView(R.id.llduration).setOnClickListener(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==9999){
            WayCheckModel Way = GlobalInformationApplication.getInstance().getCurrentWay();


            if(Way.getIndex().equals("9999"))
            {
                agentnameid = Way.getNameid();
                agentname = Way.getName();

                agentgroupnameid = Way.getGroupid();
                agentgroupname = Way.getGroupname();

                tvname.setText(Way.getName());
                tvgroupname.setText(Way.getGroupname());


            }
        }
        else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回两种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    break;
            }
        }
        switch (requestCode) {
            case REQUEST_TO_DATE_PICKER:
                if (data != null) {
                    ArrayList<Integer> list = data.getIntegerArrayListExtra(DatePickerWindow.RESULT_DATE_DETAIL_LIST);
                    if (list != null && list.size() >= 3) {
                        selectedDate = new int[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            selectedDate[i] = list.get(i);
                        }
                        startdatetag.setText(selectedDate[0] + "-" + (selectedDate[1] + 1) + "-" + selectedDate[2]);
                        selectedDate[1]=selectedDate[1]+1;
                        selectedStaraDate= selectedDate;
                    }
                }
                break;
            case REQUEST_TO_DATE_PICKEREND:
                if (data != null) {

                    ArrayList<Integer> list = data.getIntegerArrayListExtra(DatePickerWindow.RESULT_DATE_DETAIL_LIST);
                    if (list != null && list.size() >= 3) {
                        selectedDate = new int[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            selectedDate[i] = list.get(i);
                        }
                        endDateTag.setText(selectedDate[0] + "-" + (selectedDate[1] + 1) + "-" + selectedDate[2]);
                        selectedDate[1]=selectedDate[1]+1;
                        selectedEndDate= selectedDate;
                    }
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.leavetype:
                CommonUtil.toActivity(context, EmpPickerWindow.createIntent(context, "", 2,"9999",""), 9999, false);

                break;
            case R.id.llStartDateTag:
                if(startdatetag.getText().toString()=="")
                {
                    toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                            , TimeUtil.getDateDetail(System.currentTimeMillis())), REQUEST_TO_DATE_PICKER, false);
                }
                else
                {
                    toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                            ,selectedStaraDate), REQUEST_TO_DATE_PICKER, false);
                }
                break;
            case R.id.llEndDateTag:
                if(endDateTag.getText().toString()=="")
                {
                    toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                            , TimeUtil.getDateDetail(System.currentTimeMillis())), REQUEST_TO_DATE_PICKEREND, false);
                }
                else
                {
                    toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                            ,selectedEndDate), REQUEST_TO_DATE_PICKEREND, false);
                }
                break;
            case  R.id.btnsave:

                String saveflag = saveinfo();

                if (saveflag.equals(GlobalVariableApplication.UnLoginFlag))
                {
                    showShortToast(GlobalVariableApplication.UnLoginMessage);
                    Intent intent = new Intent();
                    intent.setClass(this.context, MainLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    if(saveflag.contains("成功"))
                    {
                        new AlertDialog.Builder(AgentEdit.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("")
                                .setMessage("保存成功！")
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
                        new AlertDialog.Builder(AgentEdit.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("")
                                .setMessage(saveflag.replaceAll(regEx,""))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }


                                }).create().show();
                    }

                }
                break;
            case  R.id.btnsubmit:
                String subflag = submitinfo();

                if (subflag.equals(GlobalVariableApplication.UnLoginFlag))
                {
                    showShortToast(GlobalVariableApplication.UnLoginMessage);
                    Intent intent = new Intent();
                    intent.setClass(this.context, MainLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    if(subflag.contains("成功"))
                    {
                        new AlertDialog.Builder(AgentEdit.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("")
                                .setMessage("应用成功！")
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
                        new AlertDialog.Builder(AgentEdit.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("")
                                .setMessage(subflag.replaceAll(regEx,""))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }


                                }).create().show();
                    }
                }
                break;

        }
    }

    public String saveinfo() {
        String datastring = "";
        try {

            if(agentid.length() > 0)
            {
                String methodName = "AgentSetUpdate";
                SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                        methodName);
                soapObject.addProperty("userID",userID );
                soapObject.addProperty("auditEmpID",empID );
                soapObject.addProperty("agentEmpID", agentnameid);
                soapObject.addProperty("startDate", startdatetag.getText());
                soapObject.addProperty("endDate", endDateTag.getText());

                soapObject.addProperty("agentSetID", agentid);
                soapObject.addProperty("iosid", iosid);

                HttpRequest http = new HttpRequest();
                datastring = http.httpWebService_GetString(methodName, soapObject);
                return datastring;
            }
            else
            {
                String methodName = "AgentSetADD";
                SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                        methodName);
                soapObject.addProperty("userID",userID );
                soapObject.addProperty("auditEmpID",empID );
                soapObject.addProperty("agentEmpID", agentnameid);
                soapObject.addProperty("startDate", startdatetag.getText());
                soapObject.addProperty("endDate", endDateTag.getText());

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

    public String submitinfo() {
        String datastring = "";
        try {

            //AgentSetAPP?userID=%@&auditEmpID=%@&agentEmpID=%@&startDate=%@&endDate=%@&agentSetID=%@&iosid=%@

            String methodName = "AgentSetAPP";
            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);
            soapObject.addProperty("userID",userID );
            soapObject.addProperty("auditEmpID",empID );
            soapObject.addProperty("agentEmpID", agentnameid);
            soapObject.addProperty("startDate", startdatetag.getText());
            soapObject.addProperty("endDate", endDateTag.getText());

            if(agentid.length() > 0)
            {
                soapObject.addProperty("agentSetID", agentid);
            }
            else
            {
                soapObject.addProperty("agentSetID","0");
            }


            soapObject.addProperty("iosid", iosid);
            HttpRequest http = new HttpRequest();
            datastring = http.httpWebService_GetString(methodName, soapObject);
            return datastring;
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
