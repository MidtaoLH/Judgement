package com.example.fv.judgement.app.activity.Leave;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.LeaveModel;
import com.example.fv.judgement.app.model.LeaveTypeModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.JSON;

public class LeaveType  extends BaseActivity implements View.OnClickListener{
    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private ListView leave_lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        super.onCreate(savedInstanceState);
        //TODO demo_http_list_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
        setContentView(R.layout.activity_leave_type);
        initView();
        initData();
        leave_lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter=parent.getAdapter();
                Map<String,String> map=(Map<String, String>) adapter.getItem(position);
                String code=map.get("code");
                String name= map.get("name");
                Intent intent=new Intent();
                intent.putExtra("code",code);
                intent.putExtra("name",name);
                setResult(0x001,intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        leave_lv_list = findView(R.id.leave_lv_list);
    }

    @Override
    public void initData() {
        String jsonString = GetInfo();
        List<LeaveTypeModel> LU = new ArrayList<LeaveTypeModel>();
        //json转为实体
        Type type1 = new TypeToken<List<LeaveTypeModel>>() {
        }.getType();
        LU = new Gson().fromJson(jsonString, type1);
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

        for (LeaveTypeModel bean : LU) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("code", bean.getCode());
            showitem.put("name", bean.getName());
            listitem.add(showitem);
        }
        //创建适配器
         // 第一个参数是上下文对象
         // 第二个是listitem
         // 第三个是指定每个列表项的布局文件
         // 第四个是指定Map对象中定义的两个键（这里通过字符串数组来指定）
         // 第五个是用于指定在布局文件中定义的id（也是用数组来指定）
         SimpleAdapter adapter = new SimpleAdapter(getActivity()
                 , listitem
                 , R.layout.activity_leavetype_list
                 ,new String[]{"code", "name"}
                 ,new int[]{R.id.codetag, R.id.nametag});
        leave_lv_list.setAdapter(adapter);
    }
    public String GetInfo() {
        String datastring = "";
        try {
            String methodName = "GetVacation";
            LoginUserModel model= GlobalInformationApplication.getInstance().getCurrentUser();
            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);
            //soapObject.addProperty("userID", model.getId());
            //soapObject.addProperty("iosid", model.getAdId());
            soapObject.addProperty("data","1");
            soapObject.addProperty("userID","91");
            soapObject.addProperty("iosid", "00000000-0000-0000-0000-000000000000");

            HttpRequest http = new HttpRequest();
            datastring = http.httpWebService_GetString(methodName, soapObject);
            return datastring;
        } catch (Exception e) {
            MyLog.writeLogtoFile("错误", "LeaveType", "GetInfo", e.toString(), "0");
        }
        return "";
    }
    @Override
    public void initEvent() {
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
