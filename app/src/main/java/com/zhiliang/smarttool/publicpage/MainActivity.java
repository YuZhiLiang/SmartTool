package com.zhiliang.smarttool.publicpage;

import android.content.Intent;
import android.os.Bundle;

import com.zhiliang.smarttool.BackGroundService;
import com.zhiliang.smarttool.R;
import com.zhiliang.smarttool.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        startService(new Intent(this, BackGroundService.class));
        MainFunctionAdapter mainFunctionAdapter = new MainFunctionAdapter();
        mBinding.recyclerView.setAdapter(mainFunctionAdapter);
    }
}
