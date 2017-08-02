package com.custom.chenxz.object.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.base.adapter.RvAdapter;
import com.custom.chenxz.object.databean.TextListData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/1.
 */

public class NestRecycleViewInScrollViewActivity extends AppCompatActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sv)
    NestedScrollView sv;
    private RvAdapter rvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_in_sv);
        ButterKnife.bind(this);
        rvAdapter = new RvAdapter(this, TextListData.textListDatas);
        rv.setAdapter(rvAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setSmoothScrollbarEnabled(true);
//        layoutManager.setAutoMeasureEnabled(true);
        rv.setLayoutManager(layoutManager);
//        rv.setHasFixedSize(false);
        rv.setNestedScrollingEnabled(true);
    }

}
