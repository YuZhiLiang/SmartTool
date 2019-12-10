package com.zhiliang.smarttool.publicpage;

import android.os.Bundle;

import com.zhiliang.smarttool.R;

import androidx.preference.PreferenceFragmentCompat;

public class MainFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_main);
    }
}
