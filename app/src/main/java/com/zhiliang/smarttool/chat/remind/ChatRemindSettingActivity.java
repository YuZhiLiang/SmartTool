package com.zhiliang.smarttool.chat.remind;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhiliang.smarttool.Constant;
import com.zhiliang.smarttool.R;
import com.zhiliang.smarttool.util.AccessibilityUtil;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = Constant.PATH_CHAT_REMIND_MAIN_PAGE)
public class ChatRemindSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_remind_setting);
        AccessibilityUtil.jumpToSetting(this);
    }
}
