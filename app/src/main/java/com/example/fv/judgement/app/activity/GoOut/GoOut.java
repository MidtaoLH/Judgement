package com.example.fv.judgement.app.activity.GoOut;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.MyExamineList.WaitExamineList;
import com.example.fv.judgement.app.application.GlobalMethodApplication;

public class GoOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_out);

        GlobalMethodApplication gml =new GlobalMethodApplication();
        String aa = gml.getCurrentUserId();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragment();
            }
        });
    }
    protected Fragment getFragment() {
        return WaitExamineList.createInstance(0);
    };
}
