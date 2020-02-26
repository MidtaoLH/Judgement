package com.example.fv.judgement.app.activity.BusinessTrip;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.adapter.BusinessTripListAdapter;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.BusinessTripListModel;
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

public class BusinessTripWaitList extends BaseHttpListFragment<BusinessTripListModel, ListView, BusinessTripListAdapter> implements CacheCallBack<BusinessTripListModel>
{
    //	private static final String TAG = "UserListFragment";
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private String userID, groupid,iosid,empID;
    public static final String ARGUMENT_RANGE = "ARGUMENT_RANGE";

    public static com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripWaitList createInstance(int range) {
        com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripWaitList fragment = new com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripWaitList();
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
    public void setList(final List<BusinessTripListModel> list) {
        setList(new AdapterCallBack<BusinessTripListAdapter>() {

            @Override
            public BusinessTripListAdapter createAdapter() {
                return new BusinessTripListAdapter(context);
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
        empID=model.getEmpID();
        super.initData();
    }

    @Override
    public void getListAsync(final int page) {
        int pageindex = page;
        pageindex++;

        String methodName = "GetBusinessTripInfoAndroid";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("pasgeIndex",pageindex);
        soapObject.addProperty("pageSize",GlobalVariableApplication.pageSize);
        soapObject.addProperty("userID",userID);
        soapObject.addProperty("empID",empID);
        soapObject.addProperty("menuID","0");
        soapObject.addProperty("iosid",iosid);
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        List<BusinessTripListModel> listExaData=new ArrayList<BusinessTripListModel>();

        Type type = new TypeToken<List<BusinessTripListModel>>(){}.getType();
        listExaData = new Gson().fromJson(jsonData,type);

        onHttpResponse(-page, JSON.toJSONString(listExaData), null);
        //实际使用时用这个，需要配置服务器地址		HttpRequest.getUserList(range, page, -page, this);

        //仅测试用<<<<<<<<<<<
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    onHttpResponse(-page, page >= 5 ? null : JSON.toJSONString(TestUtil.getUserList(page, getCacheCount())), null);
//                }
//            }, 1000);
        //仅测试用>>>>>>>>>>>>
    }

    @Override
    public List<BusinessTripListModel> parseArray(String json) {
        return JSON.parseArray(json, BusinessTripListModel.class);
    }
    @Override
    public Class<BusinessTripListModel> getCacheClass() {
        return BusinessTripListModel.class;
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
    public String getCacheId(BusinessTripListModel data) {
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
        toActivity(BusinessTripEdit.createIntent(context, adapter.getItem(position).getAidFK(), adapter.getItem(position).getPicID(),adapter.getItem(position).getCaseApplyCode()
                ,"2","getdata"));
    }
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            if (id > 0) {
//                toActivity(UserActivity.createIntent(context, id));
//            }
//        }
    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}

