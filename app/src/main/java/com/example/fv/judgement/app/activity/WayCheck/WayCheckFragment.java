package com.example.fv.judgement.app.activity.WayCheck;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.adapter.ExamineListAdapter;
import com.example.fv.judgement.app.adapter.WayCheckAdapter;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.WayCheckModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.OnBottomDragListener;


import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.model.Entry;

/**fragment示例
 * @author Lemon
 * @use new DemoFragment(),具体参考.DemoFragmentActivity(initData方法内)
 */
public class WayCheckFragment extends BaseFragment {
    private static final String TAG = "DemoFragment";

    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String ARGUMENT_USER_ID = "ARGUMENT_USER_ID";
    public static final String ARGUMENT_USER_NAME = "ARGUMENT_USER_NAME";

    /**创建一个Fragment实例
     * @param userId
     * @return
     */
    public static WayCheckFragment createInstance(long userId) {
        return createInstance(userId, null);
    }
    /**创建一个Fragment实例
     * @param userId
     * @param userName
     * @return
     */
    public static WayCheckFragment createInstance(long userId, String userName) {
        WayCheckFragment fragment = new WayCheckFragment();

        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_USER_ID, userId);
        bundle.putString(ARGUMENT_USER_NAME, userName);

        fragment.setArguments(bundle);
        return fragment;
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    private long userId = 0;
    private String userName = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        super.onCreateView(inflater, container, savedInstanceState);
        //TODO demo_fragment改为你所需要的layout文件
        setContentView(R.layout.way_check_fragment);

        argument = getArguments();
        if (argument != null) {
            userId = argument.getLong(ARGUMENT_USER_ID, userId);
            userName = argument.getString(ARGUMENT_USER_NAME, userName);
        }

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;//返回值必须为view
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //示例代码<<<<<<<<
    private ListView lvDemoFragment;
    //示例代码>>>>>>>>
    @Override
    public void initView() {//必须在onCreateView方法内调用

        //示例代码<<<<<<<<<<<<<<
        lvDemoFragment = findView(R.id.lvDemoFragment);
        //示例代码>>>>>>>>>>>>>>
    }

    //示例代码<<<<<<<<
    private WayCheckAdapter adapter;
    //示例代码>>>>>>>>
    /** 示例方法 ：显示列表内容
     * @author author
     * @param list
     */
    public void setList(List<WayCheckModel> list) {
        if (adapter == null) {
            adapter = new WayCheckAdapter(context);
            lvDemoFragment.setAdapter(adapter);
        }
        adapter.refresh(list);
    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //示例代码<<<<<<<<
    public List<WayCheckModel> list;
    //示例代码>>>>>>>>>
    @Override
    public void initData() {//必须在onCreateView方法内调用

        //示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        //showShortToast(TAG + ": userId = " + userId + "; userName = " + userName);

        //showProgressDialog(R.string.loading);

        runThread(TAG + "initData", new Runnable() {
            @Override
            public void run() {

                list = getList();
                runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        setList(list);
                    }
                });
            }
        });

        //示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    }


    /**示例方法：获取号码列表
     * @author lemon
     * @param
     * @return
     */
    public List<WayCheckModel> getList() {
//        list = new ArrayList<WayCheckModel>();
//        WayCheckModel test = new WayCheckModel();
//        test.setEnglishname("1111");
//        list.add(test);
//        return list;

        String methodName = "GetWay";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("id","94");
        soapObject.addProperty("processid","271");
        soapObject.addProperty("iosid","d90730e57c3bdb81");
        soapObject.addProperty("userid","94");

        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        List<WayCheckModel> LCM=new ArrayList<WayCheckModel>();

        Type type = new TypeToken<List<WayCheckModel>>(){}.getType();
        LCM = new Gson().fromJson(jsonData,type);

        return LCM;


    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须在onCreateView方法内调用
        //示例代码<<<<<<<<<<<<<<<<<<<

        lvDemoFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                switch (view.getId()) {
                    case R.id.btnadd:

                        toActivity(WayChose.createIntent(context, position));//一般用id，这里position仅用于测试 id));//
                }
               // toActivity(UserActivity.createIntent(context, position));//一般用id，这里position仅用于测试 id));//
            }
        });
        //示例代码>>>>>>>>>>>>>>>>>>>
    }




    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}




