package com.example.fv.judgement.app.activity.Notice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fv.judgement.app.activity.Login.MainLogin;
import com.example.fv.judgement.app.activity.MyExamineList.WaitExamineList;
import com.example.fv.judgement.app.adapter.ExamineListAdapter;
import com.example.fv.judgement.app.adapter.NoticeListAdapter;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.NoticeModel;
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

public class GetNotice extends BaseHttpListFragment<NoticeModel, ListView, NoticeListAdapter> implements CacheCallBack<NoticeModel>
{
    public static final String ARGUMENT_RANGE = "ARGUMENT_RANGE";

    public static GetNotice createInstance(int range) {
        GetNotice fragment = new GetNotice();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_RANGE, range);
        fragment.setArguments(bundle);
        return fragment;
    }
    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public static final int RANGE_ALL = 0;//HttpRequest.USER_LIST_RANGE_ALL;
    public static final int RANGE_RECOMMEND = 1;//HttpRequest.USER_LIST_RANGE_RECOMMEND;
    private int range = RANGE_ALL;
    private int CurentPageCount = 5;

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
    public void setList(final List<NoticeModel> list) {
        setList(new AdapterCallBack<NoticeListAdapter>() {

            @Override
            public NoticeListAdapter createAdapter() {
                return new NoticeListAdapter(context);
            }
            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    public void initData() {//必须调用
        super.initData();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //实现单选
        adapter.selectedPosition = adapter.selectedPosition == position ? -1 : position;
        adapter.notifyListDataSetChanged();

        Intent intert = new Intent(context,NoticeDetail.class);

        Bundle B = new Bundle();
        B.putString("title",adapter.getItem(position).getNewsTheme());
        B.putString("groupname",adapter.getItem(position).getG_CName());
        B.putString("date",adapter.getItem(position).getNewsDate());
        B.putString("content",adapter.getItem(position).getNewsContent());
        intert.putExtras(B);
        startActivity(intert);

    }

    @Override
    public void getListAsync(final int page) {
        //实际使用时用这个，需要配置服务器地址  HttpRequest.getUserList(range, page, -page, this);
        //LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();
        LoginUserModel LUM = GlobalInformationApplication.getInstance().getCurrentUser();

        int pageindex = page;
        pageindex++;
        CurentPageCount = 5;

        String methodName = "GetNoticeNewsAndroid";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("pasgeIndex",pageindex);
        soapObject.addProperty("pageSize",GlobalVariableApplication.pageSize);
        soapObject.addProperty("userID",LUM.getId());
        soapObject.addProperty("GroupID_FK",LUM.getGroupid());
        soapObject.addProperty("iosid",LUM.getAdId());
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
            List<NoticeModel> listExaData=new ArrayList<NoticeModel>();

            Type type = new TypeToken<List<NoticeModel>>(){}.getType();
            listExaData = new Gson().fromJson(jsonData,type);

            //框架是 每次取增量数据
            onHttpResponse(-page, JSON.toJSONString(listExaData), null);
        }

    }

    @Override
    public String getCacheId(NoticeModel data) {
        return data == null ? null : "" + data.getId();
    }

    @Override
    public List<NoticeModel> parseArray(String json) {
        return JSON.parseArray(json, NoticeModel.class);
    }

    @Override
    public Class<NoticeModel> getCacheClass() {
        return NoticeModel.class;
    }

    @Override
    public String getCacheGroup() {
        return null;
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


}
