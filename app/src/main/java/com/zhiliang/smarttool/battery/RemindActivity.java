package com.zhiliang.smarttool.battery;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhiliang.smarttool.Constant;
import com.zhiliang.smarttool.R;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = Constant.PATH_BATTERY_REMIND_REMIND_PAGE)
public class RemindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }
}
