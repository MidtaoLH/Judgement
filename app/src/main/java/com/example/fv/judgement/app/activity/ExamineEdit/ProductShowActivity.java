package com.example.fv.judgement.app.activity.ExamineEdit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.adapter.ProductListAdapter;
import com.example.fv.judgement.app.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductShowActivity extends AppCompatActivity {

    private List<Product> products;
    private ListView lvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_edit1);

        this.lvProduct = (ListView) findViewById(R.id.ExamineList);

        // 初始化产品数据
        this.products = new ArrayList<Product>();
        Product pro1 = new Product(1001,"艾静吸尘器",1200,"超静音八遍系成绩，能铣刀各个觉喽哦，不放过任何一个死角",R.drawable.pro01);
        products.add(pro1);
        Product pro2 = new Product(1002,"高级电饭锅",2388,"香喷喷的米饭由此而生",R.drawable.pro04);
        products.add(pro2);
        Product pro3 = new Product(1003,"负离子去螨器",890,"香喷喷的螨虫，你想如何处置", R.drawable.pro06);
        products.add(pro3);
        Product pro4 = new Product(1004,"多功能料理机",2359,"功能丰富，随心所欲。。。。", R.drawable.pro08);
        products.add(pro4);
        Product pro5 = new Product(1005,"多可必搅拌棒",300,"操作方便，随时随地都可以获取新鲜食物。",R.drawable.pro10);
        products.add(pro5);

        ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), products);
        this.lvProduct.setAdapter(adapter);
    }



}
