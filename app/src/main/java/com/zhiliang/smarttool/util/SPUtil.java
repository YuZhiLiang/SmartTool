package com.zhiliang.smarttool.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhiliang.smarttool.STApplication;

public class SPUtil {
    private static SPUtil INSTANCE = null;
    private SharedPreferences mSharedPreferences;
    private String mSpName = "SmartToolSharedPreferences";

    public SPUtil() {
        mSharedPreferences = STApplication.getInstance().getSharedPreferences(mSpName, Context.MODE_PRIVATE);
    }

    public static SPUtil getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new SPUtil();
        }
        return INSTANCE;
    }

    public void putInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public void putLong(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).commit();
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }

    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    public void putString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }
}
