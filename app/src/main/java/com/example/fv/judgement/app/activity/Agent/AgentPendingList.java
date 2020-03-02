package com.example.fv.judgement.app.activity.Agent;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fv.judgement.app.activity.Login.MainLogin;
import com.example.fv.judgement.app.adapter.AgentListAdapter;

import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.AgentListModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseHttpListFragment;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.CacheCallBack;
import zuo.biao.library.util.JSON;

public class AgentPendingList  extends BaseHttpListFragment<AgentListModel, ListView, AgentListAdapter> implements CacheCallBack<AgentListModel>
{
    //	private static final String TAG = "UserListFragment";
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private String userID, groupid,iosid,EmpID;
    public static final String ARGUMENT_RANGE = "ARGUMENT_RANGE";

    public static AgentPendingList createInstance(int range) {
        AgentPendingList fragment = new AgentPendingList();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_RANGE, range);
        fragment.setArguments(bundle);
        return fragment;
    }
    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public static final int RANGE_ALL = 0;//HttpRequest.USER_LIST_RANGE_ALL;
    public static final int RANGE_RECOMMEND = 1;//HttpRequest.USER_LIST_RANGE_RECOMMEND;
    private int range = RANGE_ALL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        super.onCreateView(inflater, container, savedInstanceState);
        argument = getArguments();
        if (argument != null) {
            //           range = argument.getInt(ARGUMENT_RANGE, range);
        }
        //          initCache(this);
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        srlBaseHttpList.autoRefresh();
        return view;
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须调用
        super.initView();
    }
    @Override
    //  public void setList(final List<ExamineModel> list) {
    public void setList(final List<AgentListModel> list) {
        setList(new AdapterCallBack<AgentListAdapter>() {

            @Override
            public AgentListAdapter createAdapter() {
                return new AgentListAdapter(context);
            }
            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {//必须调用
        LoginUserModel model = GlobalInformationApplication.getInstance().getCurrentUser();
        userID = model.getId();
        groupid = model.getGroupid();
        iosid =model.getAdId();
        EmpID = model.getEmpID();
        super.initData();
    }

    @Override
    public void getListAsync(final int page) {
        int pageindex = page;
        pageindex++;

        String methodName = "AgentSearchByIDAndroid";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("pasgeIndex",pageindex);
        soapObject.addProperty("pageSize",GlobalVariableApplication.pageSize);
        soapObject.addProperty("userID",userID);
        soapObject.addProperty("EmpID",EmpID);
        soapObject.addProperty("AgentSupFlag","0");

        soapObject.addProperty("iosid",iosid);
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);


        if (jsonData.equals(GlobalVariableApplication.UnLoginFlag))
        {
            showShortToast(GlobalVariableApplication.UnLoginMessage);
            Intent intent = new Intent();
            intent.setClass(this.context, MainLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
        {
            List<AgentListModel> listExaData=new ArrayList<AgentListModel>();

            Type type = new TypeToken<List<AgentListModel>>(){}.getType();
            listExaData = new Gson().fromJson(jsonData,type);

            onHttpResponse(-page, JSON.toJSONString(listExaData), null);
        }

    }

    @Override
    public List<AgentListModel> parseArray(String json) {
        return JSON.parseArray(json, AgentListModel.class);
    }
    @Override
    public Class<AgentListModel> getCacheClass() {
        return AgentListModel.class;
    }

    @Override
    public String getCacheGroup() {
        return null;
    }

    //        @Override
//        public String getCacheGroup() {
//            return "range=" + range;
//        }
    @Override
    public String getCacheId(AgentListModel data) {
        return data == null ? null : "" + data.getId();
    }
    @Override
    public int getCacheCount() {
        return 10;
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Override
    public void initEvent() {//必须调用
        super.initEvent();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        toActivity(LeaveEdit.createIntent(context, adapter.getItem(position).getAwardID_FK(), adapter.getItem(position).getProcessInstanceID(),adapter.getItem(position).getProcessApplyCode()
//                ,"2","getdata"));

        if(adapter.getItem(position).getAgentStatus().equals("1"))
        {
                //跳转到编辑页面
            toActivity(AgentEdit.createIntent(context, "", "","",""
                    ,adapter.getItem(position).getAgentSetID(),"",""));
        }
        else
        {
                //跳转到终止页面
            toActivity(AgentShow.createIntent(context, "", "","",""
                    ,adapter.getItem(position).getAgentSetID(),"",""));

        }


    }

    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
