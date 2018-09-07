package com.zhiliang.smarttool.util;

import android.util.Log;

public class LogUtil {
    private static final String DEF_TAG = "YZL";

    public static void e(String msg) {
        e(DEF_TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
