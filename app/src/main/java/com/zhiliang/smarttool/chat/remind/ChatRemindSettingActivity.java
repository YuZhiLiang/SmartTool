package com.zhiliang.smarttool.chat.remind;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.vondear.rxtool.view.RxToast;
import com.zhiliang.smarttool.AccService;
import com.zhiliang.smarttool.Constant;
import com.zhiliang.smarttool.DataBean.FunctionBean;
import com.zhiliang.smarttool.R;
import com.zhiliang.smarttool.databinding.ActivityChatRemindSettingBinding;
import com.zhiliang.smarttool.publicpage.FunctionAdapter;
import com.zhiliang.smarttool.util.AccessibilityUtil;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

@Route(path = Constant.PATH_CHAT_REMIND_MAIN_PAGE)
public class ChatRemindSettingActivity extends AppCompatActivity {
    ActivityChatRemindSettingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat_remind_setting);
        ArrayList<FunctionBean> functionBeans = new ArrayList<>();
        functionBeans.add(new FunctionBean("打开易用性服务").setProcessMode(FunctionBean.sProcessMode_Custom).setPath(Constant.CUSTOM_PATH_OPEN_ACCESSUIBILITY));
        FunctionAdapter functionAdapter = new FunctionAdapter(functionBeans);
        mBinding.recyclerView.setAdapter(functionAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        functionAdapter.setListener(functionBean -> {
            if (functionBean.getPath().equals(Constant.CUSTOM_PATH_OPEN_ACCESSUIBILITY)) {
                if (AccessibilityUtil.isSettingOpen(AccService.class, ChatRemindSettingActivity.this)) {
                    RxToast.normal(ChatRemindSettingActivity.this.getString(R.string.accessibility_service_has_open));
                } else {
                    AccessibilityUtil.jumpToSetting(this);
                }
            }
        });
    }
}
