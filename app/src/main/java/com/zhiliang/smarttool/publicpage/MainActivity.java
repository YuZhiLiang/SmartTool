package com.zhiliang.smarttool.publicpage;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhiliang.smarttool.BackGroundService;
import com.zhiliang.smarttool.Constant;
import com.zhiliang.smarttool.DataBean.FunctionBean;
import com.zhiliang.smarttool.R;
import com.zhiliang.smarttool.databinding.ActivityMainBinding;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

@Route(path = Constant.PATH_MAIN_PAGE)
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        startService(new Intent(this, BackGroundService.class));
        FunctionAdapter mainFunctionAdapter = new FunctionAdapter();
        ArrayList<FunctionBean> functionBeans = new ArrayList<>();
        functionBeans.add(new FunctionBean("电量提醒", Constant.PATH_BATTERY_REMIND_MAIN_PAGE));
        functionBeans.add(new FunctionBean("消息提醒", Constant.PATH_CHAT_REMIND_MAIN_PAGE));
        mainFunctionAdapter.setList(functionBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(mainFunctionAdapter);
    }
}
