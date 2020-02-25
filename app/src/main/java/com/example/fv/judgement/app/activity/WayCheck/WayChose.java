package com.example.fv.judgement.app.activity.WayCheck;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Spinner;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.application.Waydata;
import com.example.fv.judgement.app.manager.ControlHelper;
import com.example.fv.judgement.app.model.Emp;
import com.example.fv.judgement.app.model.Group;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WayChose extends Activity {

    private Spinner spinner1, spinner2;//声明两个下拉框
    private Button btnadd;

    private String index = "0";
    private String level = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        Intent intent=getIntent();
//根据参数名称获取参数

        Bundle B = intent.getExtras();
        index = B.getString("index");
        level = B.getString("level");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_chose);
        spinner1 = (Spinner) findViewById(R.id.spinner1);//绑定ID
        spinner2 = (Spinner) findViewById(R.id.spinner2);//绑定ID
        btnadd= (Button) findViewById(R.id.btnadd);//绑定ID

        String json = "";
        json = GetGroupWebService();

        List<Group> LU=new ArrayList<Group>();
        //json转为实体
      Type type1=new TypeToken<List<Group>>(){}.getType();
        LU = new Gson().fromJson(json, type1);

        ControlHelper.BindSpinner(getBaseContext(),spinner1,LU);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                 * ids是刚刚新建的list里面的ID
                 */
                String ids = ((Group) spinner1.getSelectedItem()).getCode();
                System.out.println(ids);
                Toast.makeText(getApplicationContext(), String.valueOf(ids), Toast.LENGTH_LONG).show();

                String json = "";
                json = GetEmpnameWebService(ids);

                List<Emp> Emp=new ArrayList<Emp>();
                //json转为实体
                Type type1=new TypeToken<List<Emp>>(){}.getType();
                Emp = new Gson().fromJson(json, type1);

                List<Emp> Emp_Use=new ArrayList<Emp>();

                for(int i = 0; i<Emp.size();i++)
                {
                    if( Emp.get(i).getGCode().toString().equals(ids))
                    {
                        Emp_Use.add(Emp.get(i));
                    }
                }
                ControlHelper.BindSpinner_2(getBaseContext(),spinner2,Emp_Use);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                if( ((Group) spinner1.getSelectedItem()).getName().length() > 0 && ((Emp) spinner2.getSelectedItem()).getName().length() > 0  )
                                {
                                    Waydata AppWaydata = (Waydata)getApplication();

                                    AppWaydata.setIndex(index);
                                    AppWaydata.setGroupname(((Group) spinner1.getSelectedItem()).getName());
                                    AppWaydata.setGroupid(((Group) spinner1.getSelectedItem()).getCode());

                                    AppWaydata.setEnglishname(((Emp) spinner2.getSelectedItem()).getENGLISHNAME());
                                    AppWaydata.setEditflag("1");
                                    AppWaydata.setName(((Emp) spinner2.getSelectedItem()).getName());
                                    AppWaydata.setLevelname(level);
                                    AppWaydata.setNameid(((Emp) spinner2.getSelectedItem()).getCode());
                                    finish();
                                }
                                else
                                {

                                }

                            }
                        });
                    }
                }).start();
            }
        });

    }

    /**获取启动UserActivity的intent
     * @param context
     * @param userId
     * @return
     */
    public static Intent createIntent(Context context, long userId) {
        return new Intent(context, WayChose.class).putExtra("INTENT_ID", userId);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.m.main, menu);
        return true;
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

    public String GetEmpnameWebService(String Code)
    {
        String datastring = "";
        try{
            String methodName = "GetEmpname";

            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);

            soapObject.addProperty("groupid", Code);
            soapObject.addProperty("AuditUsedFlag", "0");
            soapObject.addProperty("iosid", Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID));
            soapObject.addProperty("userid", 94);

            HttpRequest http=new HttpRequest();

            datastring =http.httpWebService_GetString(methodName,soapObject);

            return datastring;

        } catch (Exception e) {
            MyLog.writeLogtoFile("错误","WayChose","GetEmpnameWebService",e.toString(),"0");
        }

        return datastring;
    }


}
