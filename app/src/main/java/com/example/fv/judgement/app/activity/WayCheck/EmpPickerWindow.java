package com.example.fv.judgement.app.activity.WayCheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.application.Waydata;
import com.example.fv.judgement.app.model.Emp;
import com.example.fv.judgement.app.model.Group;
import com.example.fv.judgement.app.model.WayCheckModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseViewBottomWindow;
import zuo.biao.library.model.Entry;
import zuo.biao.library.model.GridPickerConfig;
import zuo.biao.library.ui.GridPickerAdapter;
import zuo.biao.library.ui.GridPickerView;
import zuo.biao.library.util.PlaceUtil;

public class EmpPickerWindow extends BaseViewBottomWindow<List<Entry<Integer, String>>, GridPickerView> {
    private static final String TAG = "EmpPickerWindow";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String INTENT_MIN_LEVEL = "INTENT_MIN_LEVEL";//最小深度 省/... - minLevel = 0; 市/... - minLevel=1;
    public static final String INTENT_MAX_LEVEL = "INTENT_MAX_LEVEL";//最大深度 ...市/ - maxLevel = 1;.../乡(街) - maxLevel=3;

    public static final String RESULT_PLACE_LIST = "RESULT_PLACE_LIST";


    public  List<Group> Group ;
    public  List<Emp> Emp;


    /**启动这个Activity的Intent
     * @param context
     * @param packageName
     * @param maxLevel
     * @return
     */
    public static Intent createIntent(Context context, String packageName, int maxLevel,String index ,String level) {
        return createIntent(context, packageName, 0, maxLevel,index,level);
    }
    /**启动这个Activity的Intent
     * @param context
     * @param packageName
     * @param minLevel
     * @param maxLevel
     * @return
     */
    public static Intent createIntent(Context context, String packageName, int minLevel, int maxLevel,String index ,String level) {
        return new Intent(context, EmpPickerWindow.class).
                putExtra(INTENT_PACKAGE_NAME, packageName).
                putExtra(INTENT_MIN_LEVEL, minLevel).
                putExtra(INTENT_MAX_LEVEL, maxLevel).
                putExtra("index", index).
                putExtra("level", level)
                ;
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public static final String INTENT_PACKAGE_NAME = "INTENT_PACKAGE_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        //获取部门的list
        String json = "";
        json = GetGroupWebService();

        Group=new ArrayList<Group>();
        //json转为实体
        Type type1=new TypeToken<List<Group>>(){}.getType();
        Group = new Gson().fromJson(json, type1);

        //获取员工的list
        json = "";
        json = GetEmpnameWebService("");

        Emp=new ArrayList<Emp>();
        //json转为实体
        Type type2=new TypeToken<List<Emp>>(){}.getType();
        Emp = new Gson().fromJson(json, type2);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>




    }

    public String GetEmpnameWebService(String Code)
    {
        String datastring = "";
        try{
            String methodName = "GetEmpname";

            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);

            soapObject.addProperty("groupid", Code);
            soapObject.addProperty("AuditUsedFlag", "0");
            soapObject.addProperty("iosid", Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID));//
            soapObject.addProperty("userid", 94);

            HttpRequest http=new HttpRequest();

            datastring =http.httpWebService_GetString(methodName,soapObject);

            return datastring;

        } catch (Exception e) {
            MyLog.writeLogtoFile("错误","WayChose","GetEmpnameWebService",e.toString(),"0");
        }

        return datastring;
    }

    public String GetGroupWebService()
    {
        String datastring = "";
        try{
            String methodName = "GetGroup";

            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);

            HttpRequest http=new HttpRequest();

            datastring =http.httpWebService_GetString(methodName,soapObject);

            return datastring;

        } catch (Exception e) {
            MyLog.writeLogtoFile("错误","WayChose","GetGroupWebService",e.toString(),"0");
        }

        return datastring;
    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initView() {//必须调用
        super.initView();

    }


    private List<Entry<Integer, String>> list;
    private void setPickerView(final int tabPosition, final int itemPositon) {
        runThread(TAG + "setPickerView", new Runnable() {
            @Override
            public void run() {

                list = getList(tabPosition, containerView.getSelectedItemList());
                runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        containerView.bindView(tabPosition, list, itemPositon);
                    }
                });
            }
        });
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private int minLevel;
    private int maxLevel;
    private String index;
    private String levelname;

    //private CityDB cityDB;
    @Override
    public void initData() {//必须调用
        super.initData();

        minLevel = getIntent().getIntExtra(INTENT_MIN_LEVEL, 0);
        maxLevel = getIntent().getIntExtra(INTENT_MAX_LEVEL, 2);

        index = getIntent().getStringExtra("index");
        levelname = getIntent().getStringExtra("level");

        if (maxLevel < 0 || minLevel > maxLevel) {
            Log.e(TAG, "initData maxLevel < 0 || minLevel > maxLevel >> finish(); return; ");
            finish();
            return;
        }
        if (minLevel < 0) {
            minLevel = 0;
        }

        runThread(TAG + "initData", new Runnable() {

            @Override
            public void run() {
                final ArrayList<GridPickerConfig> configList = new ArrayList<GridPickerConfig>();

                String GroupName = "";
                String GroupCode = "";
                String EmpName ="";
                String EmpId = "";
                if(Group.size() > 0)
                {
                    GroupName = Group.get(0).getName();
                    GroupCode = Group.get(0).getCode();
                }
                if(Emp.size() > 0)
                {
                    for(int i = 0; i<Emp.size();i++)
                    {
                        if( Emp.get(i).getGCode().toString().equals(GroupCode))
                        {
                            if(EmpName.length() > 0)
                            {

                            }
                            else
                            {
                                EmpName = Emp.get(i).getName();
                                EmpId = Emp.get(i).getCode();
                            }

                        }
                    }
                }


                configList.add(new GridPickerConfig("", GroupName, 10));
                configList.add(new GridPickerConfig("", EmpName, 0));

                final ArrayList<String> selectedItemNameList = new ArrayList<String>();
                for (GridPickerConfig gpcb : configList) {
                    selectedItemNameList.add(gpcb.getSelectedItemName());
                }

                list = getList(selectedItemNameList.size() - 1, selectedItemNameList);
                runUiThread(new Runnable() {

                    @Override
                    public void run() {
                        containerView.init(configList, list);
                    }
                });
            }
        });

    }


    private synchronized List<Entry<Integer, String>> getList(int tabPosition, ArrayList<String> selectedItemList) {
        int level = minLevel + tabPosition;
        if (selectedItemList == null || selectedItemList.size() <= 0 || PlaceUtil.isContainLevel(level) == false) {
            return null;
        }
        list = new ArrayList<Entry<Integer, String>>();
        List<String> nameList =  new ArrayList<String>();;

        try{

            switch (level) {
                case PlaceUtil.LEVEL_PROVINCE:

                    for(int i = 0; i<Group.size();i++)
                    {
                        nameList.add(Group.get(i).getName());
                    }

                    break;
                case PlaceUtil.LEVEL_CITY:
                    String GCode = "";
                    for(int i = 0; i<Group.size();i++)
                    {
                        if(selectedItemList.get(0).equals(Group.get(i).getName()))
                        {
                            GCode = Group.get(i).getCode();
                        }
                    }

                    for(int i = 0; i<Emp.size();i++)
                    {
                        if( Emp.get(i).getGCode().toString().equals(GCode))
                        {
                            String aa = Emp.get(i).getName();
                            nameList.add(aa);
                        }
                    }

                    break;
                case PlaceUtil.LEVEL_DISTRICT:
                    break;
                case PlaceUtil.LEVEL_TOWN:
                    break;
                case PlaceUtil.LEVEL_ROAD:
                    break;
                default:
                    break;
            }

            if (nameList != null) {
                for (String name : nameList) {
                    list.add(new Entry<Integer, String>(GridPickerAdapter.TYPE_CONTNET_ENABLE, name));
                }
            }
            return list;
        }
        catch (Exception e)
        {
            return list;
        }


    }



    @Override
    public String getTitleName() {
        return "选择员工";
    }
    @Override
    public String getReturnName() {
        return null;
    }
    @Override
    public String getForwardName() {
        return null;
    }

    @Override
    protected GridPickerView createView() {
        return new GridPickerView(context);
    }

    @Override
    protected void setResult() {

        ArrayList<String> list = new ArrayList<String>();
        list= containerView.getSelectedItemList();

        WayCheckModel Way = new WayCheckModel();

        //Waydata AppWaydata = (Waydata)getApplication();
        if(list.size() ==2)
        {
            String groupname = list.get(0).toString();
            String empname = list.get(1).toString();
            String groupid = "";

            for(int i = 0; i<Group.size();i++)
            {
               if (groupname.equals(Group.get(i).getName()))
               {
                   Way.setIndex(index);
                   Way.setGroupname(groupname);
                   groupid = Group.get(i).getCode();
                   Way.setGroupid(groupid);
                   Way.setEditflag("1");
                   Way.setName(empname);
               }
            }

            for(int i = 0; i<Emp.size();i++)
            {
                if( Emp.get(i).getGCode().toString().equals(groupid) && Emp.get(i).getName().toString().equals(empname))
                {
                    Way.setEnglishname(Emp.get(i).getENGLISHNAME());
                    Way.setNameid(Emp.get(i).getCode());
                }
            }

            if(levelname.equals("一级审批人"))
            {
                Way.setLevel("1");
                Way.setLevelname("一级审批人");
            }
            else if(levelname.equals("二级审批人"))
            {
                Way.setLevel("2");
                Way.setLevelname("二级审批人");
            }
            else if(levelname.equals("三级审批人"))
            {
                Way.setLevel("3");
                Way.setLevelname("三级审批人");
            }
            else if(levelname.equals("四级审批人"))
            {
                Way.setLevel("4");
                Way.setLevelname("四级审批人");
            }
            else if(levelname.equals("五级审批人"))
            {
                Way.setLevel("5");
                Way.setLevelname("五级审批人");
            }
            else if(levelname.equals("六级审批人"))
            {
                Way.setLevel("6");
                Way.setLevelname("六级审批人");
            }
            else if(levelname.equals("七级审批人"))
            {
                Way.setLevel("7");
                Way.setLevelname("七级审批人");
            }
            else
            {
                Way.setLevel("99");
                Way.setLevelname("回览人");
            }

            GlobalInformationApplication.getInstance().saveCurrentWay(Way);




        }





        setResult(RESULT_OK, new Intent().putStringArrayListExtra(
                RESULT_PLACE_LIST, containerView.getSelectedItemList()));
    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用
        super.initEvent();

        containerView.setOnTabClickListener(onTabClickListener);
        containerView.setOnItemSelectedListener(onItemSelectedListener);
    }


    private GridPickerView.OnTabClickListener onTabClickListener = new GridPickerView.OnTabClickListener() {

        @Override
        public void onTabClick(int tabPosition, TextView tvTab) {
            setPickerView(tabPosition, containerView.getSelectedItemPosition(tabPosition));
        }
    };

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
            containerView.doOnItemSelected(containerView.getCurrentTabPosition()
                    , position, containerView.getCurrentSelectedItemName());
            setPickerView(containerView.getCurrentTabPosition() + 1, 0);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    };


    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //	cityDB = null;
    }


    //类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
