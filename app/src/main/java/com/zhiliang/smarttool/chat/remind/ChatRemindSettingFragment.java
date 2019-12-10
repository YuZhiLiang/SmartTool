package com.zhiliang.smarttool.chat.remind;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.vondear.rxtool.view.RxToast;
import com.zhiliang.smarttool.AccService;
import com.zhiliang.smarttool.R;
import com.zhiliang.smarttool.util.AccessibilityUtil;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class ChatRemindSettingFragment extends PreferenceFragmentCompat {
    private int mChatRemindKey = R.string.pref_key_chat_remind;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_chat_remind, rootKey);
        Preference chatRemindPreference = findPreference(getString(mChatRemindKey));
        if (chatRemindPreference != null) {
            chatRemindPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if (newValue instanceof Boolean) {
                    if ((Boolean) newValue) {
                        if (AccessibilityUtil.isSettingOpen(AccService.class, getContext())) {
                            RxToast.normal(getString(R.string.accessibility_service_has_open));
                        } else {
                            showAccessibilityServiceDialog();
                            return true;
                        }
                    } else {
                        AccService.setActionBack(false);
                    }
                } else {
                    AccService.setActionBack(false);
                }
                return true;
            });
        }

        Preference openAccessibilityPreference = findPreference(getString(R.string.pref_key_open_accessibility_service));
        if (openAccessibilityPreference != null) {
            openAccessibilityPreference.setOnPreferenceClickListener(preference -> {
                if (AccessibilityUtil.isSettingOpen(AccService.class, getContext())) {
                    RxToast.normal(getString(R.string.accessibility_service_has_open));
                } else {
                    AccessibilityUtil.jumpToSetting(getContext());
                }
                return true;
            });
        }
    }

    private void showAccessibilityServiceDialog() {
        Context context = getContext();
        if (context != null) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.chat_remind)
                    .setMessage(R.string.chat_remind_open_accessibility_service_tips)
                    .setPositiveButton(R.string.accessibility_open_dialog_positive_text, (dialog, which) -> {
                        AccessibilityUtil.jumpToSetting(getContext());
                        AccService.setActionBack(true);
                    })
                    .setNegativeButton(R.string.accessibility_open_dialog_negative_text, (dialog, which) -> {
                        SwitchPreferenceCompat chatRemindPreference = findPreference(getString(mChatRemindKey));
                        if (chatRemindPreference != null) chatRemindPreference.setChecked(false);
                        dialog.dismiss();
                    })
                    .show();
        } else {
            Log.e("yzl", "试图弹出Dialog时获取到的Context为null");
        }
    }
}
