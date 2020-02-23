package com.example.fv.judgement.app.activity.Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fv.judgement.R;

public class PageHome extends AppCompatActivity {
    private Button myApplybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_home);

        myApplybtn  = findViewById(R.id.myApply);
        myApplybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //此处写入操作
                            }
                        });

                    }
                }).start();
            }
        });

    }
}
