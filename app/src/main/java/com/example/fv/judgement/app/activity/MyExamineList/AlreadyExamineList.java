package com.example.fv.judgement.app.activity.MyExamineList;

import android.os.StrictMode;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import zuo.biao.library.base.BaseActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.ApplyEdit.ApplyTaskLookActivity;
import com.example.fv.judgement.app.activity.ExamineEdit.ExamineEdit;
import com.example.fv.judgement.app.activity.Login.MainLogin;
import com.example.fv.judgement.app.adapter.ExamineListAdapter;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.view.ExamineListView;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseHttpListFragment;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView.OnDataChangedListener;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.CacheCallBack;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.manager.CacheManager;
import zuo.biao.library.ui.BottomMenuView;
import zuo.biao.library.ui.BottomMenuView.OnBottomMenuItemClickListener;
import zuo.biao.library.ui.BottomMenuWindow;
import zuo.biao.library.ui.EditTextInfoActivity;
import zuo.biao.library.ui.TextClearSuit;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlreadyExamineList extends BaseHttpListFragment<ExamineModel, ListView, ExamineListAdapter> implements CacheCallBack<ExamineModel>
{
//	private static final String TAG = "UserListFragment";
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String ARGUMENT_RANGE = "ARGUMENT_RANGE";
    private String userID, groupid,iosid,code;
    public static AlreadyExamineList createInstance(int range) {
        AlreadyExamineList fragment = new AlreadyExamineList();
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
    public void setList(final List<ExamineModel> list) {
        setList(new AdapterCallBack<ExamineListAdapter>() {

            @Override
            public ExamineListAdapter createAdapter() {
                return new ExamineListAdapter(context);
            }
            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //点击跳转
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String statusname =  adapter.getItem(position).getCaseName();

        startActivity(ApplyTaskLookActivity.createIntent(context, "1", adapter.getItem(position).getPicID()));
    }
    @Override
    public void initData() {//必须调用
        LoginUserModel model = GlobalInformationApplication.getInstance().getCurrentUser();
        userID = model.getId();
        groupid = model.getGroupid();
        iosid =model.getAdId();
        code=model.getEmpID();
        super.initData();
    }

    @Override
    public void getListAsync(final int page) {
        int pageindex = page;
        pageindex++;

        String methodName = "GetPendingInfoAndroid";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("pasgeIndex",pageindex);
        soapObject.addProperty("pageSize",GlobalVariableApplication.pageSize);
        soapObject.addProperty("code",code);
        soapObject.addProperty("userID",userID);
        soapObject.addProperty("menuID","6");
        soapObject.addProperty("iosid",iosid);
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        if (jsonData.equals(GlobalVariableApplication.UnLoginFlag))
        {
            showShortToast(GlobalVariableApplication.UnLoginFlag);
            Intent intent = new Intent();
            intent.setClass(this.context, MainLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(jsonData.length()>0)  {
            List<ExamineModel> listExaData=new ArrayList<ExamineModel>();
            Type type = new TypeToken<List<ExamineModel>>(){}.getType();
            listExaData = new Gson().fromJson(jsonData,type);
            onHttpResponse(-page, JSON.toJSONString(listExaData), null);
        }
    }

    @Override
    public List<ExamineModel> parseArray(String json) {
        return JSON.parseArray(json, ExamineModel.class);
    }
    @Override
    public Class<ExamineModel> getCacheClass() {
        return ExamineModel.class;
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
    public String getCacheId(ExamineModel data) {
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

    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
