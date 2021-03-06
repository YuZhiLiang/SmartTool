package com.zhiliang.smarttool.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zhiliang.smarttool.STApplication;

public class SPUtil {
    private static SPUtil INSTANCE = null;
    private SharedPreferences mSharedPreferences;

    private SPUtil() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(STApplication.getInstance());
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

    public void putBoolean(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }
}
