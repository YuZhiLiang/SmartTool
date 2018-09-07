package com.zhiliang.smarttool;

import android.app.Application;

public class STApplication extends Application {
    private static STApplication SMART_TOOL_APPLICATION;

    @Override
    public void onCreate() {
        super.onCreate();
        SMART_TOOL_APPLICATION = this;
    }

    public static STApplication getInstance() {
        return SMART_TOOL_APPLICATION;
    }
}
