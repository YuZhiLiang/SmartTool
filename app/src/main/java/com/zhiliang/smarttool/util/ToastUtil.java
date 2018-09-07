package com.zhiliang.smarttool.util;

import android.widget.Toast;

import com.zhiliang.smarttool.STApplication;

public class ToastUtil {
    public static void toast(String msg) {
        Toast.makeText(STApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
