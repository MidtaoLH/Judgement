package com.example.fv.judgement.app.manager;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.fv.judgement.app.model.Emp;
import com.example.fv.judgement.app.model.Group;

import java.util.List;

public class ControlHelper {

    public static <T extends AdapterView> void BindSpinner(Context context, T control, List<Group> list)
    {
        ArrayAdapter<Group> adapter=new ArrayAdapter<Group>(context, android.R.layout.simple_spinner_dropdown_item,list);
        ((T)control).setAdapter(adapter);
    }
    public static <T extends AdapterView> void BindSpinner(Context context, T control, List<Group> list,Integer layout)
    {
        ArrayAdapter<Group> adapter=new ArrayAdapter<Group>(context, layout,list);
        ((T)control).setAdapter(adapter);
    }

    public static <T extends AdapterView> void BindSpinner_2(Context context, T control, List<Emp> list)
    {
        ArrayAdapter<Emp> adapter=new ArrayAdapter<Emp>(context, android.R.layout.simple_spinner_dropdown_item,list);
        ((T)control).setAdapter(adapter);
    }
    public static <T extends AdapterView> void BindSpinner_2(Context context, T control, List<Emp> list,Integer layout)
    {
        ArrayAdapter<Emp> adapter=new ArrayAdapter<Emp>(context, layout,list);
        ((T)control).setAdapter(adapter);
    }


}
