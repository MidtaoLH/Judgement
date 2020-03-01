package com.example.fv.judgement.app.activity.GoOut;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.MyExamineList.WaitExamineList;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.model.LoginUserModel;

public class GoOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_out);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUserModel model=new LoginUserModel();
                model.setEmpID("aa");
                GlobalInformationApplication.getInstance().saveCurrentUser(model);

                String id=GlobalInformationApplication.getInstance().getCurrentUserId();
            }
        });
    }
}
