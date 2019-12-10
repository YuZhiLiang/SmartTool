package com.zhiliang.smarttool.chat.remind;

import android.os.Bundle;

import com.zhiliang.smarttool.R;

import androidx.preference.PreferenceFragmentCompat;

public class ChatRemindSettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_chat_remind);
    }
}
